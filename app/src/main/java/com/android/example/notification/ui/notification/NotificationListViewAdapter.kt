package com.android.example.notification.ui.notification

import android.view.View
import com.android.example.notification.room.data.NotificationTableData
import com.android.example.notification.ui.base.list.BaseRecycleViewAdapter

class NotificationListViewAdapter(layoutResourceId: Int, items: ArrayList<NotificationTableData>, init: (View, NotificationTableData) -> Unit) :
    BaseRecycleViewAdapter<NotificationTableData>(layoutResourceId, items, init)
