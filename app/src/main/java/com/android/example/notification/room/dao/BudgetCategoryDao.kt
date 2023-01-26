package com.android.example.notification.room.dao


import androidx.room.*
import com.android.example.notification.data.BudgetCategoryData


@Dao
interface BudgetCategoryDao {
    @Query(
        "SELECT * FROM budget_table" +
                " JOIN category_table ON budget_table.budget_category = category_table.category"
    )
    fun getAll(): List<BudgetCategoryData>
}
