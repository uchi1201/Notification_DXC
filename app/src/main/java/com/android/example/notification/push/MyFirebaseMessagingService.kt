package com.android.example.notification.push


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.android.example.notification.MainActivity
import com.android.example.notification.R
import com.android.example.notification.constant.MyConstant
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        var messageData:Map<String, String>? = null

        Log.d(TAG, "From: ${remoteMessage?.from}")

        // Check if message contains a data payload.
        remoteMessage?.data?.isNotEmpty()?.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            messageData = remoteMessage.data
        }

        // Check if message contains a notification payload.
        remoteMessage?.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(messageData,it.title.toString(),it.body.toString())
        }
    }
    private fun getPendingIntent(context: Context, messageData:Map<String, String>?): PendingIntent? {

        val destId: Int = R.id.navigation_manage
        val bundle = Bundle()
        bundle.putString("money",messageData?.get("money").toString())
        bundle.putString("date",messageData?.get("date").toString())
        bundle.putString("address",messageData?.get("address").toString())
        bundle.putString("category",messageData?.get("category").toString())

        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(destId)
            .setArguments(bundle)
            .createPendingIntent()
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
    private fun sendNotification(messageData:Map<String, String>?,messageTitle:String,messageBody:String) {

        val views = RemoteViews(packageName, R.layout.layout_notification)
        views.setTextViewText(R.id.date,messageData?.get("date"))
        views.setTextViewText(R.id.shop_name_txt,messageData?.get("address"))
        views.setTextViewText(R.id.category_tx,messageData?.get("category"))
        views.setTextViewText(R.id.money_tx,messageData?.get("money"))
        val penIntent =getPendingIntent(this,messageData)
        val notificationBuilder = NotificationCompat.Builder(this, MyConstant.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setCustomBigContentView(views)
            .setAutoCancel(true)
            .setContentIntent(penIntent)


        val notificationManager = getSystemService(NotificationManager::class.java)
        // Since android Oreo notification channel is needed.
        notificationManager.notify(MyConstant.CHANNEL_ID, 1, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "【Firebaseテスト中】"
    }
}