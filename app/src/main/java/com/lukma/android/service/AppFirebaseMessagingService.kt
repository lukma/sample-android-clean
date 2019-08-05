package com.lukma.android.service

import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lukma.android.R
import com.lukma.android.domain.common.UseCaseConstant
import com.lukma.android.domain.preference.usecase.SaveFcmUseCase
import com.lukma.android.presentation.main.MainActivity
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

class AppFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {
    private val saveFcmUseCase by inject<SaveFcmUseCase>()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notification = NotificationCompat
                .Builder(this, FCM_DEFAULT_CHANNEL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .build()

            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFICATION_ID, notification)
            }
        }
    }

    override fun onNewToken(token: String?) {
        token?.let {
            val params = mapOf(UseCaseConstant.TOKEN to it)
            saveFcmUseCase.addParams(params).execute()
        }
    }

    companion object {
        private const val FCM_DEFAULT_CHANNEL = "FCM_DEFAULT_CHANNEL"
        private const val NOTIFICATION_ID = 0
    }
}
