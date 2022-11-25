package com.android.example.notification.data

data class BudgetValueBean(val data: BudgetGraphData)
data class BudgetGraphData(
    val budget:Float,
    val budgetTotal: Float
)