package com.android.example.notification.data

data class CategoryData(
    val code: Int,
    val `data`: Data,
    val msg: String
)

data class Data(
    val dataList: List<DataX>
)

data class DataX(
    val category: String,
    val color: String
)