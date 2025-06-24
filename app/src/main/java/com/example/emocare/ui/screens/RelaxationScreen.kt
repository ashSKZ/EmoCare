package com.example.emocare.ui.screens

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.emocare.R
import com.example.emocare.ui.navigation.NavRoutes


@Composable
fun RelaxationScreen(navController: NavController) {

    val context = LocalContext.current

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.breathing))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.relaxing_sound) }
    var isPlaying by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(250.dp)
        )

        Text("Ejercicio de respiración", fontSize = 22.sp, style = MaterialTheme.typography.titleMedium)
        Text(
            "Inhala 4s - Mantén 7s - Exhala 8s",
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium
        )

        Button(
            onClick = {
                if (!isPlaying) {
                    mediaPlayer.start()
                } else {
                    mediaPlayer.pause()
                }
                isPlaying = !isPlaying
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isPlaying) "Pausar sonido" else "Reproducir sonido")
        }

        ElevatedButton(
            onClick = {
                navController.navigate(NavRoutes.ExploreExercises.route)
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SelfImprovement,
                contentDescription = "Más ejercicios"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Explorar otros ejercicios")
        }

    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}
