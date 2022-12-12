package com.android.example.notification.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.notification.data.CategoryData

class CategoryManagementViewModel  : ViewModel(){
    val categoryData = MutableLiveData<MutableList<CategoryData>>()

    fun getCategoryDataList(){

    }

}