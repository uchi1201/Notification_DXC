package com.android.example.notification.network



import com.android.example.notification.data.NotificationBean
import com.android.example.notification.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface INetworkService {

    @GET("dev/notification_data")
    suspend fun requestNotificationInfo(): BaseResponse<NotificationBean>



}