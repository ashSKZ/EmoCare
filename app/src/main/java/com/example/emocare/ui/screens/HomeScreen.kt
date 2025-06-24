package com.example.emocare.ui.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.emocare.ui.navigation.NavRoutes
import com.example.emocare.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.util.*

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    RequestAudioPermission()

    val context = LocalContext.current
    val inputText by viewModel.inputText.collectAsState()
    val detectedEmotion by viewModel.detectedEmotion.collectAsState()
    val isDetectEnabled = inputText.trim().isNotEmpty()

    val coroutineScope = rememberCoroutineScope()

    val apiKey = "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"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿Cómo te sientes hoy?",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = inputText,
            onValueChange = viewModel::onTextChange,
            label = { Text("Escribe lo que sientes...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            maxLines = 6,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = {
                    iniciarReconocimientoVoz(context) { textoDetectado ->
                        viewModel.onTextChange(textoDetectado)
                    }
                },
                modifier = Modifier
                    .size(72.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Micrófono",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.detectEmotionFromAPI(context, apiKey)
                        viewModel.detectedEmotion.value?.let {
                            navController.navigate(
                                NavRoutes.Result.createRoute(
                                    it.emotion,
                                    it.confidence,
                                    URLEncoder.encode(it.recommendation, "UTF-8")
                                )
                            )
                        }
                    }
                },
                enabled = isDetectEnabled,
                modifier = Modifier
                    .height(56.dp)
                    .defaultMinSize(minWidth = 180.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Detectar emoción", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun RequestAudioPermission() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Permiso de micrófono denegado", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.RECORD_AUDIO)
    }
}

fun iniciarReconocimientoVoz(context: Context, onResultado: (String) -> Unit) {
    val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
    }

    speechRecognizer.setRecognitionListener(object : RecognitionListener {
        override fun onResults(results: Bundle?) {
            val texto = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull()
            texto?.let { onResultado(it) }
        }

        override fun onError(error: Int) {
            Toast.makeText(context, "Error en reconocimiento: $error", Toast.LENGTH_SHORT).show()
        }

        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onPartialResults(partialResults: Bundle?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
    })

    speechRecognizer.startListening(intent)
}
