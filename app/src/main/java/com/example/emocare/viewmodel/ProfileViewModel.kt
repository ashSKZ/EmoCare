package com.example.emocare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.emocare.data.UserPreferences
import com.example.emocare.data.UserProfile
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = UserPreferences(application)

    val profile = prefs.userProfile
        .stateIn(viewModelScope, SharingStarted.Lazily, UserProfile())

    fun save(profile: UserProfile) = viewModelScope.launch {
        prefs.saveProfile(profile)
    }
}
