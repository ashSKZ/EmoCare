package com.example.emocare.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emocare.api.detectAndSaveEmotion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.emocare.data.AppDatabase
import com.example.emocare.data.EmotionRecord
import kotlin.random.Random

data class EmotionResult(
    val emotion: String,
    val confidence: Float,
    val recommendation: String
)

class HomeViewModel : ViewModel() {

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val _detectedEmotion = MutableStateFlow<EmotionResult?>(null)
    val detectedEmotion: StateFlow<EmotionResult?> = _detectedEmotion

    fun onTextChange(newText: String) {
        _inputText.value = newText
    }

    fun detectEmotionFromAPI(context: Context, apiKey: String) {
        viewModelScope.launch {
            val result = detectAndSaveEmotion(inputText.value, context, apiKey)
            result?.let {
                _detectedEmotion.value = it
            }
        }
    }



    fun clearEmotion() {
        _detectedEmotion.value = null
    }
}

