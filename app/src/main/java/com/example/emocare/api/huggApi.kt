package com.example.emocare.api

import android.content.Context
import com.example.emocare.data.AppDatabase
import com.example.emocare.data.EmotionRecord
import com.example.emocare.viewmodel.EmotionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

suspend fun detectEmotionHuggingFace(text: String, apiKey: String): EmotionResult? = withContext(Dispatchers.IO) {
    val url = URL("https://api-inference.huggingface.co/models/j-hartmann/emotion-english-distilroberta-base")
    val connection = url.openConnection() as HttpURLConnection

    connection.requestMethod = "POST"
    connection.setRequestProperty("Authorization", "Bearer $apiKey")
    connection.setRequestProperty("Content-Type", "application/json")
    connection.doOutput = true

    val input = """{"inputs": "$text"}"""
    connection.outputStream.use { it.write(input.toByteArray()) }

    return@withContext try {
        val response = connection.inputStream.bufferedReader().use { it.readText() }
        val list = JSONArray(response).getJSONArray(0)
        if (list.length() == 0) return@withContext null

        // Debug log (opcional)
        for (i in 0 until list.length()) {
            val item = list.getJSONObject(i)
            println(">> EMOTION: ${item.getString("label")} score=${item.getDouble("score")}")
        }

        val best = (0 until list.length()).map { list.getJSONObject(it) }
            .maxByOrNull { it.getDouble("score") }

        if (best != null) {
            val label = best.getString("label")
            val score = best.getDouble("score").toFloat()

            EmotionResult(
                emotion = mapEmotion(label),
                confidence = score,
                recommendation = generateRecommendation(label)
            )
        } else null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun mapEmotion(label: String): String = when (label.lowercase(Locale.ROOT)) {
    "joy", "happiness", "optimism" -> "alegría"
    "sadness", "grief" -> "tristeza"
    "anger", "annoyance", "rage" -> "enojo"
    "fear", "nervousness", "worry", "anxiety" -> "ansiedad"
    "calm", "peacefulness", "relief" -> "calma"
    "surprise", "amazement" -> "sorpresa"
    "disgust", "contempt" -> "rechazo"
    else -> "desconocida"
}

fun generateRecommendation(label: String): String = when (label.lowercase(Locale.ROOT)) {
    "joy" -> "¡Sigue disfrutando tu día!"
    "sadness" -> "Está bien no estar bien. Cuida de ti."
    "anger" -> "Respira y tómate un momento antes de actuar."
    "fear" -> "Busca apoyo y respira profundo."
    "surprise" -> "Tómate un momento para procesarlo."
    "disgust" -> "Haz algo que te reconforte y te conecte."
    else -> "Exprésate libremente, estás seguro aquí."
}

suspend fun detectAndSaveEmotion(text: String, context: Context, apiKey: String): EmotionResult? {
    val result = detectEmotionHuggingFace(text, apiKey)
    if (result != null) {
        val dao = AppDatabase.getDatabase(context).emotionDao()
        withContext(Dispatchers.IO) {
            dao.insert(
                EmotionRecord(
                    emotion = result.emotion,
                    confidence = result.confidence,
                    recommendation = result.recommendation,
                    inputText = text
                )
            )
        }
    }
    return result
}
