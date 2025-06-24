package com.example.emocare.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.airbnb.lottie.compose.*
import com.example.emocare.R
import java.net.URLDecoder

@Composable
fun ResultScreen(
    entry: NavBackStackEntry,
    onBack: () -> Unit
) {
    val emotion = entry.arguments?.getString("emotion") ?: "desconocida"
    val confidence = entry.arguments?.getString("confidence")?.toFloatOrNull() ?: 0f
    val recommendationRaw = entry.arguments?.getString("recommendation") ?: ""
    val recommendation = URLDecoder.decode(recommendationRaw, "UTF-8")

    // Asocia emoción a recurso local
    val animationResId = when (emotion.lowercase()) {
        "alegría" -> R.raw.happy
        "tristeza" -> R.raw.sad
        "enojo" -> R.raw.angryy
        "ansiedad" -> R.raw.anxiety
        "calma" -> R.raw.calm
        else -> R.raw.calm
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Resultado emocional", style = MaterialTheme.typography.headlineSmall)

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(200.dp)
                .padding(vertical = 16.dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = emotion.replaceFirstChar { it.uppercase() },
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text("Confianza: ${(confidence * 100).toInt()}%", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(24.dp))

            Text("Recomendación", style = MaterialTheme.typography.labelMedium)
            Text(
                text = recommendation,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 12.dp),
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}
