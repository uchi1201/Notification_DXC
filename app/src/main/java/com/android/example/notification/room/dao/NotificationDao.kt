package com.android.example.notification.room.dao


import androidx.room.*
import com.android.example.notification.room.data.NotificationTableData


@Dao
interface NotificationDao {
    @Query("SELECT * FROM notification_table")
    fun getAll(): List<NotificationTableData>

    @Query("SELECT * FROM notification_table WHERE shopName IN (:notificationList)")
    fun loadAllByNumbers(notificationList: List<String>): List<NotificationTableData>

    @Query("SELECT * FROM notification_table WHERE shopName  = :shopName")
    fun findByNumber(shopName: String): NotificationTableData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notificationTableData:NotificationTableData)

    @Update
    fun update(notificationTableData: NotificationTableData)

    @Delete
    fun delete( notificationTableData: NotificationTableData?):Int

    @Query("delete from notification_table")
    fun deleteAll()
}