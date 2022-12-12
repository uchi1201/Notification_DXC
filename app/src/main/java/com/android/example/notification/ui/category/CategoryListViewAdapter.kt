package com.android.example.notification.ui.category

import android.view.View
import com.android.example.notification.data.BudgetData
import com.android.example.notification.data.CategoryData
import com.android.example.notification.data.NotificationData
import com.example.practice.base.list.BaseRecycleViewAdapter

class CategoryListViewAdapter(layoutResourceId: Int, items: List<CategoryData>, init: (View, CategoryData) -> Unit) :
    BaseRecycleViewAdapter<CategoryData>(layoutResourceId, items, init)
