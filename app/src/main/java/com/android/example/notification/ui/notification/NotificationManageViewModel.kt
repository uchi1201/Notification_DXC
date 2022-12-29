package com.android.example.notification.ui.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.notification.data.NotificationBean
import com.android.example.notification.network.NetworkApiTest
import com.android.example.notification.room.MyDataBase
import com.android.example.notification.room.NotificationDataBase
import com.android.example.notification.room.data.NotificationTableData
import kotlinx.coroutines.launch

class NotificationManageViewModel(dataBase: NotificationDataBase) : ViewModel() {
    private val notificationsListLiveData = MutableLiveData<Result<NotificationBean>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val pullToRefreshLiveData = MutableLiveData<Boolean>()
    private val notificationDao = dataBase.notificationDao()
    var dbNotificationData = ArrayList<NotificationTableData>()


    fun getNotificationDataFromDB(){
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            dbNotificationData = notificationDao.getAll() as ArrayList<NotificationTableData>
            loadingLiveData.postValue(false)
        }
    }

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