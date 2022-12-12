package com.android.example.notification.ui.category

import android.view.View
import com.android.example.notification.data.CategoryData
import com.android.example.notification.data.DataX
import com.example.practice.base.list.BaseRecycleViewAdapter

class CategoryListViewAdapter(layoutResourceId: Int, items: List<DataX>, init: (View, DataX) -> Unit) :
    BaseRecycleViewAdapter<DataX>(layoutResourceId, items, init)
