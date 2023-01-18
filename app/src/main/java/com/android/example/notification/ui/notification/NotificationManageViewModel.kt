package com.android.example.notification.ui.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.notification.room.NotificationDataBase
import com.android.example.notification.room.data.NotificationTableData
import kotlinx.coroutines.launch

class NotificationManageViewModel(dataBase: NotificationDataBase) : ViewModel() {
    val loadingLiveData = MutableLiveData<Boolean>()
    val pullToRefreshLiveData = MutableLiveData<Boolean>()
    private val notificationDao = dataBase.notificationDao()
    var dbNotificationData = ArrayList<NotificationTableData>()

    /**
     * DBのデータを全部検索
     */
    fun getNotificationDataFromDB(){
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            dbNotificationData = notificationDao.getAll() as ArrayList<NotificationTableData>
            loadingLiveData.postValue(false)
        }
    }

    /**
     * プルダウンのデータを再読み込み
     */
    fun getPTRNotificationsList() {
        pullToRefreshLiveData.postValue(true)
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            dbNotificationData = notificationDao.getAll() as ArrayList<NotificationTableData>
            pullToRefreshLiveData.postValue(false)
            loadingLiveData.postValue(false)
        }
    }

}