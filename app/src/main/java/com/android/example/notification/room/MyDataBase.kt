package com.android.example.notification.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.example.notification.room.dao.CategoryDao
import com.android.example.notification.room.data.CategoryData

@Database(entities = [CategoryData::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun cardLoginDao(): CategoryDao
}