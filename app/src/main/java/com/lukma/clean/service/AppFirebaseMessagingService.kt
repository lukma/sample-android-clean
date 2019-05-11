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
import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.domain.preference.usecase.SaveFcmUseCase
import com.lukma.clean.ui.main.MainActivity
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

class AppFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {
    private val saveFcmUseCase by inject<SaveFcmUseCase>()

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

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder.build())
        }
    }

    override fun onNewToken(token: String?) {
        saveFcmUseCase.execute(mapOf(UseCaseConstant.TOKEN to token))
    }

    companion object {
        private const val FCM_DEFAULT_CHANNEL = "FCM_DEFAULT_CHANNEL"
    }
}
