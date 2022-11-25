package com.android.example.notification.network

import com.android.example.notification.network.INetworkService
import com.android.example.notification.network.base.BaseNetworkApi

/**
 * ネットワーク請求実施
 * サーバーのURL：
 */
object NetworkApi : BaseNetworkApi<INetworkService>("https://d664bb3e-e2c0-4643-bfcf-8cb71ce79026.mock.pstmn.io") {

    //お知らせAPIをコール
//    suspend fun requestNotificationInfo() = getResult {
//        service.requestNotificationInfo()
//    }
}