package com.android.example.notification.room.dao


import androidx.room.*
import com.android.example.notification.room.data.CategoryData


@Dao
interface CategoryDao {
    @Query("SELECT * FROM card_login_table")
    fun getAll(): List<CategoryData>

    @Query("SELECT * FROM card_login_table WHERE cardNum IN (:cardNumbers)")
    fun loadAllByNumbers(cardNumbers: List<String>): List<CategoryData>

    @Query("SELECT * FROM card_login_table WHERE cardNum  = :cardNum")
    fun findByNumber(cardNum: String): CategoryData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cardLoginData:CategoryData)

    @Update
    fun update(cardLoginData: CategoryData)
}