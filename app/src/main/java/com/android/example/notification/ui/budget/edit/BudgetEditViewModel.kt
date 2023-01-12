package com.android.example.notification.ui.budget.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BudgetEditViewModel : ViewModel() {

    val totalBudget = MutableLiveData<Int>()

    fun getCategoryList(): ArrayList<String>{
        //Todo サーバー側からもらうか
        //一旦仮データ
        var categorySpList: ArrayList<String> = ArrayList()
        categorySpList.add("服飾費")
        categorySpList.add("食費")
        categorySpList.add("交際")
        categorySpList.add("定期支払い")
        categorySpList.add("水道")
        categorySpList.add("カテゴリ名を選択")
        return categorySpList
    }
}