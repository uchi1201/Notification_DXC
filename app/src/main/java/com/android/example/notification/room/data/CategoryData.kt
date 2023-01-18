package com.android.example.notification.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * カテゴリーテーブルのフィールド定義
 * @property category String
 * @property color String
 * @constructor
 */
@Entity(tableName = "category_table")
data class CategoryData(
    @PrimaryKey
    var category:String,
    @ColumnInfo(name = "colorName")
    var color:String
)
