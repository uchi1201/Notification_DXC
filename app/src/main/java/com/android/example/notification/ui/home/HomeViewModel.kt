package com.android.example.notification.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.notification.data.NotificationBean
import com.android.example.notification.network.NetworkApiTest
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val notificationsListLiveData = MutableLiveData<Result<NotificationBean>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val pullToRefreshLiveData = MutableLiveData<Boolean>()

    fun getPTRNotificationsList() {
        pullToRefreshLiveData.postValue(true)
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            val notificationsData =
                NetworkApiTest("https://bcc44455-3c2c-4c72-b417-470a1c5e2842.mock.pstmn.io")
            val requestValue = notificationsData.requestNotificationInfo()
            notificationsListLiveData.value = requestValue
            pullToRefreshLiveData.postValue(false)
            loadingLiveData.postValue(false)
        }
    }
}