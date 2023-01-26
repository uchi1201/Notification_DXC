package com.android.example.notification.data

import androidx.room.Embedded
import com.android.example.notification.room.data.BudgetTableData
import com.android.example.notification.room.data.CategoryData

class BudgetCategoryData {
    @Embedded
    var budgetTableData: BudgetTableData? = null

    @Embedded
    var categoryData: CategoryData? = null
}
