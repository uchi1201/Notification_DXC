package com.android.example.notification.ui.budget

import android.view.View
import com.android.example.notification.data.BudgetData
import com.example.practice.base.list.BaseRecycleViewAdapter

class BudgetListViewAdapter(layoutResourceId: Int, items: List<BudgetData>, init: (View, BudgetData) -> Unit) :
    BaseRecycleViewAdapter<BudgetData>(layoutResourceId, items, init)
