package com.lukma.clean.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lukma.clean.R
import com.lukma.clean.ui.main.MainActivity

class AppFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private const val FCM_DEFAULT_CHANNEL = "FCM_DEFAULT_CHANNEL"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent
                .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat
                .Builder(this, FCM_DEFAULT_CHANNEL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder.build())
        }
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
    }
}