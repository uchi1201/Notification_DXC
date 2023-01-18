package com.android.example.notification.data

import com.android.example.notification.room.data.CategoryData

/**
 * Roomで追加カテゴリーデータ用
 * @property code Int
 * @property `data` Data
 * @property msg String
 * @constructor
 */
data class CategoryListData(
    val code: Int,
    val `data`: Data,
    val msg: String
)

data class Data(
    val dataList: List<CategoryData>
)
