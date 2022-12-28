package com.android.example.notification.ui.notification.division

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.notification.data.NotificationBean
import com.android.example.notification.network.NetworkApiTest
import com.android.example.notification.room.MyDataBase
import com.android.example.notification.room.NotificationDataBase
import com.android.example.notification.room.data.NotificationTableData
import kotlinx.coroutines.launch

class NotificationDivisionViewModel() : ViewModel() {

    fun getCategoryList(): ArrayList<String>{
        //Todo サーバー側からもらうか
        //一旦仮データ
        var categorySpList: ArrayList<String> = ArrayList()
        categorySpList.add("食費")
        categorySpList.add("水道")
        categorySpList.add("その他")
        return categorySpList
    }


}