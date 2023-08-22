package com.studyproject.mydailydiary.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.ui.MainActivity
import com.studyproject.mydailydiary.ui.MainActivity.Companion.CHANNEL_ID
import com.studyproject.mydailydiary.ui.MainActivity.Companion.CHANNEL_NAME
import com.studyproject.mydailydiary.ui.MainActivity.Companion.EXTRA_NOTIFY
import com.studyproject.mydailydiary.ui.MainActivity.Companion.NOTIFICATION
import com.studyproject.mydailydiary.ui.MainActivity.Companion.NOTIFICATION_TEXT

class NotificationWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val text = inputData.getString(NOTIFICATION_TEXT)
        val id = inputData.getLong(EXTRA_NOTIFY, 0)
        createNotificationChannel()
        sendNotification(id, text)
        return Result.success()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notification_channel"
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(id: Long, text: String?) {
        //интент для открытия приложения из напоминания
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra(EXTRA_NOTIFY, id)
        intent.action = NOTIFICATION
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications)
            .setContentTitle(applicationContext.getString(R.string.notification))
            .setContentText(text)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(id.toInt(), builder.build())
    }
}