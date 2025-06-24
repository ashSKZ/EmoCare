package com.example.emocare.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class UserProfile(
    val nickname: String = "",
    val age: Int = 0,
    val avatarId: Int = 1
)

private val Context.dataStore by preferencesDataStore(name = "user_profile")

class UserPreferences(private val context: Context) {
    companion object {
        private val KEY_NICKNAME = stringPreferencesKey("nickname")
        private val KEY_AGE = intPreferencesKey("age")
        private val KEY_AVATAR = intPreferencesKey("avatar_id")
    }

    val userProfile: Flow<UserProfile> = context.dataStore.data.map { prefs ->
        UserProfile(
            nickname = prefs[KEY_NICKNAME] ?: "",
            age = prefs[KEY_AGE] ?: 0,
            avatarId = prefs[KEY_AVATAR] ?: 1
        )
    }

    suspend fun saveProfile(profile: UserProfile) {
        context.dataStore.edit { prefs ->
            prefs[KEY_NICKNAME] = profile.nickname
            prefs[KEY_AGE] = profile.age
            prefs[KEY_AVATAR] = profile.avatarId
        }
    }
}
