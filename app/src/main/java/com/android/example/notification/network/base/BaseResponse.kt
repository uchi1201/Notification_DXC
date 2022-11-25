package com.android.example.notification.network.base

/**
 * ネットワーク応答
 */
data class BaseResponse<T>(
    var code: Int = 0,
    val msg: String? = null,
    val data: T? = null
)
