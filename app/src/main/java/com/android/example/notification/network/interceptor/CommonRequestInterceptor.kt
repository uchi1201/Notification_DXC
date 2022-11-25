package com.android.example.notification.network.interceptor

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response

class CommonRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        // 請求へーだー
//        builder.addHeader("", "")
        return chain.proceed(builder.build())
    }
}