package com.example.emocare.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emocare.data.EmotionRecord
import com.example.emocare.viewmodel.HistoryViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.collectAsState

import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = viewModel()) {
    val groupedRecords by viewModel.records.collectAsState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Historial emocional", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        if (groupedRecords.isEmpty()) {
            Text("Sin registros aún.", style = MaterialTheme.typography.bodyLarge)
        } else {
            groupedRecords.forEach { (date, records) ->
                Text(
                    text = formatDate(date),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                records.forEach { record ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Emoción: ${record.emotion}", style = MaterialTheme.typography.titleSmall)
                            Text("Confianza: ${(record.confidence * 100).toInt()}%")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Texto: ${record.inputText}", style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row {
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = {
                                    scope.launch { viewModel.delete(record) }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Borrar")
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { scope.launch { viewModel.clear() } },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Limpiar todo")
            }
        }
    }
}

fun formatDate(rawDate: String): String {
    val today = SimpleDateFormat("yyyy-MM-dd").format(Date())
    val yesterday = Calendar.getInstance().apply { add(Calendar.DATE, -1) }
        .let { SimpleDateFormat("yyyy-MM-dd").format(it.time) }

    return when (rawDate) {
        today -> "Hoy"
        yesterday -> "Ayer"
        else -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            .format(SimpleDateFormat("yyyy-MM-dd").parse(rawDate) ?: Date())
    }
}
