package com.android.example.notification.ui.notification

import android.view.View
import com.android.example.notification.data.BudgetData
import com.android.example.notification.data.NotificationData
import com.example.practice.base.list.BaseRecycleViewAdapter

class NotificationListViewAdapter(layoutResourceId: Int, items: List<NotificationData>, init: (View, NotificationData) -> Unit) :
    BaseRecycleViewAdapter<NotificationData>(layoutResourceId, items, init)
