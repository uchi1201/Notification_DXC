package com.android.example.notification.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.notification.MainApplication
import com.android.example.notification.data.NotificationBean
import com.android.example.notification.network.NetworkApiTest
import com.android.example.notification.room.data.NotificationTableData
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var notificationsListLiveData = MutableLiveData<NotificationTableData>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val pullToRefreshLiveData = MutableLiveData<Boolean>()
    var currentMoney = MutableLiveData<String>()
    var remainMoney = MutableLiveData<String>()

    fun getPTRNotificationsList() {
        pullToRefreshLiveData.postValue(true)
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            MainApplication.instance().notificationDataBase?.notificationDao()?.getAll()
            pullToRefreshLiveData.postValue(false)
            loadingLiveData.postValue(false)
        }
    }

    fun getCurrentMoney(money:String){
        currentMoney.value = money
    }

    fun getRemainMoney(total:String,current:String){
        remainMoney.value = (total.toInt() - current.toInt()).toString()
    }

}