package com.android.example.notification.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 通知テーブルのフィールド定義
 * @property shopName String
 * @property dateTime String?
 * @property category String?
 * @property money String?
 * @constructor
 */
@Entity(tableName = "notification_table")
data class NotificationTableData(
    @PrimaryKey
    var shopName:String,
    @ColumnInfo(name = "dateTime")
    var dateTime:String?,
    @ColumnInfo(name = "category")
    var category:String?,
    @ColumnInfo(name = "money")
    var money:String?
)
