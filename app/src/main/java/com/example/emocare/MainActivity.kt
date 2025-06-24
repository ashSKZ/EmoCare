package com.example.emocare

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import com.example.emocare.ui.MainScreen
import com.example.emocare.worker.NotificationWorker
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private lateinit var notificationPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Registrar launcher de permisos
        registerPermissionLauncher()

        // Solicitar permiso si es necesario
        requestNotificationPermission()

        // Programar notificación diaria
        scheduleDailyNotification()

        // UI
        setContent {
            MainScreen()
        }
    }

    private fun registerPermissionLauncher() {
        notificationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            // Puedes manejar el resultado aquí si quieres mostrar algo al usuario
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun scheduleDailyNotification() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            24, TimeUnit.HOURS
        ).setInitialDelay(10, TimeUnit.SECONDS).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "daily_emocare_notification",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
