package com.android.example.notification.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from)

        Log.d(TAG, "From: ${remoteMessage?.from}")

        // Check if message contains a data payload.
        remoteMessage?.data?.isNotEmpty()?.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        // Check if message contains a notification payload.
        remoteMessage?.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body.toString())
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If we want to send messages to this application instance or
        // manage the app's subscription on the server-side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }
    private fun sendRegistrationToServer(token: String?) {
    }
    private fun sendNotification(messageBody: String) {
//        val intent = Intent(this, PushTestActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        intent.putExtra("title",getString(R.string.fcm_message))
//        intent.putExtra("msg",messageBody)
//        val penIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        val channelId = getString(R.string.default_notification_channel_id)
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
//            .setContentTitle(getString(R.string.fcm_message))
//            .setContentText(messageBody)
//            .setAutoCancel(true)
//            .setContentIntent(penIntent)


//        val notificationManager = getSystemService(NotificationManager::class.java)
//
//        // Since android Oreo notification channel is needed.
//        val channel = NotificationChannel(
//            channelId,
//            "Channel human readable title",
//            NotificationManager.IMPORTANCE_DEFAULT
//        )
//        notificationManager.createNotificationChannel(channel)
//
//        notificationManager.notify(channelId, 1, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "【Firebaseテスト中】"
    }
}