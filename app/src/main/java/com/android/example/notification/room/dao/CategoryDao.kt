package com.android.example.notification.room.dao


import androidx.room.*
import com.android.example.notification.room.data.CategoryData


@Dao
interface CategoryDao {
    //DB中の全体データを取得
    @Query("SELECT * FROM category_table")
    fun getAll(): List<CategoryData>
    //categoryによりデータリストを取得
    @Query("SELECT * FROM category_table WHERE category IN (:categoryList)")
    fun loadAllByNumbers(categoryList: List<String>): List<CategoryData>
    //categoryによりデータを取得
    @Query("SELECT * FROM category_table WHERE category  = :categoryName")
    fun findByNumber(categoryName: String): CategoryData
    //DBにデータを追加
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categoryData:CategoryData)
    //データを更新
    @Update
    fun update(categoryData: CategoryData)
    //データカラーを更新
    @Query("update category_table set colorName = :color where category = :category")
    fun updateColorForCategoryData(color:String,category:String)
    //データを削除
    @Delete
    fun delete( categoryData: CategoryData?):Int
}