package com.android.example.notification.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.notification.MainApplication
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
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