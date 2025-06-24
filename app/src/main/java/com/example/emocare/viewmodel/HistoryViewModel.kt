package com.example.emocare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.emocare.data.AppDatabase
import com.example.emocare.data.EmotionRecord
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).emotionDao()

    val records = dao.getAll()
        .map { records ->
            records.groupBy { record -> record.timestamp.toLocalDateString() }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())

    fun delete(record: EmotionRecord) = viewModelScope.launch {
        dao.delete(record)
    }

    fun clear() = viewModelScope.launch {
        dao.clear()
    }
}

// ✅ Utilidad para formatear fechas a "yyyy-MM-dd"
fun Long.toLocalDateString(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(this))
}
