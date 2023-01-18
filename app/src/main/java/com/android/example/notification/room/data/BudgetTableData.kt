package com.android.example.notification.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 予算テーブルのフィールド定義
 * @property category String　主Key
 * @property budget Float
 * @property budgetTotal Int
 * @constructor
 */
@Entity(tableName = "budget_table")
data class BudgetTableData(
    @PrimaryKey
    var category: String,
    @ColumnInfo(name = "budget")
    var budget:Float,
    @ColumnInfo(name = "budgetTotal")
    var budgetTotal:Int
)
