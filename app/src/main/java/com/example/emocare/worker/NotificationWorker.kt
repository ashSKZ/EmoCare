package com.example.emocare.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import java.util.concurrent.TimeUnit

class NotificationWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {

    override fun doWork(): Result {
        val phrases = listOf(
            "Confía en ti. Hoy es un buen día para avanzar.",
            "Respira profundo. Estás haciendo lo mejor que puedes.",
            "Eres más fuerte de lo que crees.",
            "Hoy es un gran día para intentarlo de nuevo.",
            "Tu bienestar emocional es importante."
        )

        val phrase = phrases.random()
        showNotification("EmoCare", phrase)
        return Result.success()
    }

    private fun showNotification(title: String, content: String) {
        val channelId = "emocare_channel"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "EmoCare Notificaciones",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .build()

        manager.notify(1, notification)
    }
}
