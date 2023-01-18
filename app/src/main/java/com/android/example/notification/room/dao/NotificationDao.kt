package com.android.example.notification.room.dao


import androidx.room.*
import com.android.example.notification.room.data.NotificationTableData


@Dao
interface NotificationDao {
    //通知DBからデータリストを検索
    @Query("SELECT * FROM notification_table")
    fun getAll(): List<NotificationTableData>
    //店舗名によりデータリストを検索
    @Query("SELECT * FROM notification_table WHERE shopName IN (:notificationList)")
    fun loadAllByNumbers(notificationList: List<String>): List<NotificationTableData>
    //検索店舗名
    @Query("SELECT * FROM notification_table WHERE shopName  = :shopName")
    fun findByNumber(shopName: String): NotificationTableData
    //データを追加
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notificationTableData:NotificationTableData)
    //データを更新
    @Update
    fun update(notificationTableData: NotificationTableData)
    //データを削除
    @Delete
    fun delete( notificationTableData: NotificationTableData?):Int

    @Query("delete from notification_table")
    fun deleteAll()
}