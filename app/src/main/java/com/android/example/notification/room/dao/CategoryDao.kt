package com.android.example.notification.room.dao


import androidx.room.*
import com.android.example.notification.room.data.CategoryData


@Dao
interface CategoryDao {
    @Query("SELECT * FROM category_table")
    fun getAll(): List<CategoryData>

    @Query("SELECT * FROM category_table WHERE category IN (:categoryList)")
    fun loadAllByNumbers(categoryList: List<String>): List<CategoryData>

    @Query("SELECT * FROM category_table WHERE category  = :categoryName")
    fun findByNumber(categoryName: String): CategoryData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categoryData:CategoryData)

    @Update
    fun update(categoryData: CategoryData)
}