package com.android.example.notification.data

data class NotificationBean(
    val notificationList :List<NotificationData>
)
data class NotificationData(
    val date: String,
    val shopName: String,
    val category: String,
    val money:String
)
