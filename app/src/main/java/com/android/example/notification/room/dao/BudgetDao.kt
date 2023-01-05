package com.android.example.notification.room.dao


import androidx.room.*
import com.android.example.notification.room.data.BudgetTableData


@Dao
interface BudgetDao {
    @Query("SELECT * FROM budget_table")
    fun getAll(): List<BudgetTableData>

    @Query("SELECT * FROM budget_table WHERE category IN (:budgetList)")
    fun loadAllByNumbers(budgetList: List<String>): List<BudgetTableData>

    @Query("SELECT * FROM budget_table WHERE category  = :category")
    fun findByNumber(category: String): BudgetTableData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(budgetTableData:BudgetTableData)

    @Update
    fun update(budgetTableData: BudgetTableData)

    @Delete
    fun delete( budgetTableData: BudgetTableData?):Int

    @Query("delete from budget_table")
    fun deleteAll()
}