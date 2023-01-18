package com.android.example.notification.room.dao


import androidx.room.*
import com.android.example.notification.room.data.BudgetTableData


@Dao
interface BudgetDao {
    //DB中の全体データを検索
    @Query("SELECT * FROM budget_table")
    fun getAll(): List<BudgetTableData>
    //categoryによりデータリストを検索
    @Query("SELECT * FROM budget_table WHERE category IN (:budgetList)")
    fun loadAllByNumbers(budgetList: List<String>): List<BudgetTableData>
    //categoryによりデータを検索
    @Query("SELECT * FROM budget_table WHERE category  = :category")
    fun findByNumber(category: String): BudgetTableData
    //DBにデータを追加
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(budgetTableData:BudgetTableData)
    //データを更新
    @Update
    fun update(budgetTableData: BudgetTableData)
    //データを削除
    @Delete
    fun delete( budgetTableData: BudgetTableData?):Int

    @Query("delete from budget_table")
    fun deleteAll()
}