package com.android.example.notification.ui.category

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.notification.data.CategoryListData

import com.android.example.notification.json.ReadJsonFile
import com.android.example.notification.room.MyDataBase
import com.android.example.notification.room.data.CategoryData
import kotlinx.coroutines.launch


class CategoryManagementViewModel(dataBase: MyDataBase)  : ViewModel(){
    val categoryData = MutableLiveData<CategoryListData>()
    var categoryDbData = ArrayList<CategoryData>()
    private val jsonFileName = "getCategoryListData.json"
    private var readJsonFile= ReadJsonFile(jsonFileName)
    private val categoryDao = dataBase.categoryDao()


    fun insertDataBaseData(context: Context){
        val result = readJsonFile.getCategoryListData(context, CategoryListData::class.java)
        for(item in result?.data?.dataList!!){
            categoryDao.insert(item)
        }
    }

    fun getAllCategoryData(){
        categoryDbData = categoryDao.getAll() as ArrayList<CategoryData>
    }
}