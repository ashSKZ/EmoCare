package com.example.emocare.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emotion_records")
data class EmotionRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val emotion: String,
    val confidence: Float,
    val recommendation: String,
    val inputText: String,
    val timestamp: Long = System.currentTimeMillis()
)
