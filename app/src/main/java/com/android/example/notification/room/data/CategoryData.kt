package com.android.example.notification.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class CategoryData(
    @PrimaryKey
    var category:String,
    @ColumnInfo(name = "colorName")
    var color:String
)
