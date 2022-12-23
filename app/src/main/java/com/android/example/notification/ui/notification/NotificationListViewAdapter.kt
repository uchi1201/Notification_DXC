package com.android.example.notification.ui.notification

import android.content.Context
import android.view.View
import androidx.navigation.Navigation.findNavController
import com.android.example.notification.R
import com.android.example.notification.data.NotificationData
import com.android.example.notification.ui.base.list.BaseRecycleViewAdapter

class NotificationListViewAdapter(layoutResourceId: Int, items: ArrayList<NotificationData>, init: (View, NotificationData) -> Unit) :
    BaseRecycleViewAdapter<NotificationData>(layoutResourceId, items, init)
