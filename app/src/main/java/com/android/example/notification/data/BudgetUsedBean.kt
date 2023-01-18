package com.android.example.notification.data

/**
 * 円グラフの表示データ用
 * @property data MutableList<BudgetData>
 * @constructor
 */
data class BudgetUsedBean(val data: MutableList<BudgetData>)
data class BudgetData(
    var category: String,
    var budget: String,
    var percentage: String
)
