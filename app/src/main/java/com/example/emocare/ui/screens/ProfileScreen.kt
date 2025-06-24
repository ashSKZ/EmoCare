package com.example.emocare.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emocare.R
import com.example.emocare.viewmodel.HistoryViewModel

@Composable
fun ProfileScreen(viewModel: HistoryViewModel = viewModel()) {
    val groupedRecords by viewModel.records.collectAsState()

    // Extraer todas las emociones del historial
    val allEmotions = groupedRecords.values.flatten().map { it.emotion }

    // Contar frecuencia de emociones
    val topEmotions = allEmotions
        .groupingBy { it }
        .eachCount()
        .toList()
        .sortedByDescending { it.second }
        .take(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar centrado
        Image(
            painter = painterResource(id = R.drawable.wolfchan),
            contentDescription = "Avatar del usuario",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(140.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
                .aspectRatio(1f)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Ash", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Edad: 20 años", fontSize = 16.sp)
        Text("Género: Mujer", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Text("Tus emociones más frecuentes", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        if (topEmotions.isEmpty()) {
            Text("Aún no hay emociones registradas.", style = MaterialTheme.typography.bodyMedium)
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                topEmotions.forEach { (emotion, count) ->
                    EmotionBar(emocion = emotion, cantidad = count)
                }
            }
        }
    }
}

@Composable
fun EmotionBar(emocion: String, cantidad: Int) {
    val maxVisibleValue = 10  // puedes ajustarlo dinámicamente si deseas

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("$emocion ($cantidad veces)", fontSize = 14.sp)
        Box(
            modifier = Modifier
                .fillMaxWidth((cantidad / maxVisibleValue.toFloat()).coerceIn(0f, 1f))
                .height(16.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
        )
    }
}
