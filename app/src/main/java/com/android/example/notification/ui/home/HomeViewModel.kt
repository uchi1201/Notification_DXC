package com.android.example.notification.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.notification.MainApplication
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val loadingLiveData = MutableLiveData<Boolean>()
    val pullToRefreshLiveData = MutableLiveData<Boolean>()
    //今月の利用額
    var currentMoney = MutableLiveData<String>()
    //残り金額
    var remainMoney = MutableLiveData<String>()

    /**
     * データを読み込み
     */
    fun getPTRNotificationsList() {
        pullToRefreshLiveData.postValue(true)
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            MainApplication.instance().notificationDataBase?.notificationDao()?.getAll()
            pullToRefreshLiveData.postValue(false)
            loadingLiveData.postValue(false)
        }
    }

    /**
     * 今月の利用額を取得
     * @param money String
     */
    fun getCurrentMoney(money:String){
        currentMoney.value = money
    }

    /**
     * 残り額を計算する
     * @param total String
     * @param current String
     */
    fun getRemainMoney(total:String,current:String){
        remainMoney.value = (total.toInt() - current.toInt()).toString()
    }

}