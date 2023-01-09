package com.android.example.notification.data

data class BudgetValueBean(val data: BudgetGraphData)
data class BudgetGraphData(
    val category: String,
    val budget:Float,
    val budgetTotal: Float
)