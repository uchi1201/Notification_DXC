package com.android.example.notification.data

data class BudgetUsedBean(val data: MutableList<BudgetData>)
data class BudgetData(
    var category: String,
    var budget: String,
    var percentage: String
)
