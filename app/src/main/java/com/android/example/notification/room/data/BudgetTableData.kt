package com.android.example.notification.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "budget_table")
data class BudgetTableData(
    @PrimaryKey
    var category: String,
    @ColumnInfo(name = "budget")
    var budget:Float,
    @ColumnInfo(name = "budgetTotal")
    var budgetTotal:Int
)
