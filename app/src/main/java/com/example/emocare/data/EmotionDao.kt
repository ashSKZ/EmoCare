package com.example.emocare.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionDao {
    @Insert
    suspend fun insert(record: EmotionRecord)

    @Delete
    suspend fun delete(record: EmotionRecord)

    @Query("DELETE FROM EmotionRecord")
    suspend fun clear()

    @Query("SELECT * FROM EmotionRecord ORDER BY timestamp DESC")
    fun getAll(): Flow<List<EmotionRecord>> // ✅ Importante: debe ser Flow
}
