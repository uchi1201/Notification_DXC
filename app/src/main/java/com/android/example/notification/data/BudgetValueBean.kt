package com.android.example.notification.data

/**
 * 横棒グラフの描画用
 * @property data BudgetGraphData
 * @constructor
 */
data class BudgetValueBean(val data: BudgetGraphData)
data class BudgetGraphData(
    val category: String,
    val budget:Float,
    val budgetTotal: Float
)