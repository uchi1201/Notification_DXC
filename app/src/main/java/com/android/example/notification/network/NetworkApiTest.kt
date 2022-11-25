package com.android.example.notification.network

import com.android.example.notification.network.INetworkService

import com.android.example.notification.network.base.BaseNetworkApi

class NetworkApiTest(url:String) : BaseNetworkApi<INetworkService>(url){

    //お知らせAPIをコール
    suspend fun requestNotificationInfo() = getResult {
        service.requestNotificationInfo()
    }

}