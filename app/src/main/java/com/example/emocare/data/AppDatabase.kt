package com.example.emocare.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmotionRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emotionDao(): EmotionDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "emocare_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
