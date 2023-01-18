package com.android.example.notification.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.example.notification.room.dao.NotificationDao
import com.android.example.notification.room.data.NotificationTableData
/**
 * 通知DBの作成
 */
@Database(entities = [NotificationTableData::class], version = 1,exportSchema = false)
abstract class NotificationDataBase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
}