package com.android.example.notification.ui.category

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.notification.data.CategoryData
import com.android.example.notification.json.ReadJsonFile
import kotlinx.coroutines.launch


class CategoryManagementViewModel  : ViewModel(){
    val categoryData = MutableLiveData<CategoryData>()
    private val jsonFileName = "getCategoryListData.json"
    private var readJsonFile= ReadJsonFile(jsonFileName)

    fun getCategoryDataList(context: Context){
        viewModelScope.launch {
            val result = readJsonFile.getCategoryListData(context,CategoryData::class.java)
            categoryData.value=result!!
        }
    }

}