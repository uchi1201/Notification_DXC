package com.android.example.notification.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.example.notification.data.NotificationData
import com.android.example.notification.room.dao.BudgetDao
import com.android.example.notification.room.dao.CategoryDao
import com.android.example.notification.room.dao.NotificationDao
import com.android.example.notification.room.data.BudgetTableData
import com.android.example.notification.room.data.CategoryData
import com.android.example.notification.room.data.NotificationTableData

@Database(entities = [BudgetTableData::class], version = 1,exportSchema = false)
abstract class BudgetDataBase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
}