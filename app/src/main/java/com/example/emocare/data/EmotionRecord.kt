package com.example.emocare.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmotionRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val emotion: String,
    val confidence: Float,
    val recommendation: String,
    val inputText: String,
    val timestamp: Long = System.currentTimeMillis() // ✅ necesario para agrupar por fecha
)
