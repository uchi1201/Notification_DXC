package com.android.example.notification.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.example.notification.room.dao.BudgetCategoryDao
import com.android.example.notification.room.dao.BudgetDao
import com.android.example.notification.room.dao.CategoryDao
import com.android.example.notification.room.data.BudgetTableData
import com.android.example.notification.room.data.CategoryData
/**
 * カテゴリーDBの作成
 */
@Database(entities = [CategoryData::class, BudgetTableData::class], version = 2,exportSchema = false)
abstract class MyDataBase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun budgetDao(): BudgetDao
    abstract fun budgetCategoryDao(): BudgetCategoryDao
}