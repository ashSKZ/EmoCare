package com.example.emocare.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.emocare.ui.navigation.NavRoutes

data class RelaxationExercise(
    val title: String,
    val description: String
)

val sampleExercises = listOf(
    RelaxationExercise(
        title = "Meditación guiada",
        description = "Sigue una voz calmada que te guía a un estado de paz interior."
    ),
    RelaxationExercise(
        title = "Visualización",
        description = "Imagina un paisaje relajante para reducir el estrés."
    ),
    RelaxationExercise(
        title = "Respiración consciente",
        description = "Ejercicio de respiración 4-7-8 para relajarte."
    ),
    RelaxationExercise(
        title = "Escaneo corporal",
        description = "Lleva tu atención a cada parte del cuerpo y libera tensión."
    )
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreExercisesScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explorar ejercicios") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.SelfImprovement, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            sampleExercises.forEach { exercise ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Aquí podrías navegar a un ejercicio específico
                            navController.navigate(NavRoutes.Relaxation.route)
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = exercise.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = exercise.description)
                    }
                }
            }
        }
    }
}
