package com.android.example.notification

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.android.example.notification.room.BudgetDataBase
import com.android.example.notification.room.MyDataBase
import com.android.example.notification.room.NotificationDataBase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainApplication: Application()  {

    var spinnerMonth:String = "1"
    var notificationDataBase:NotificationDataBase? = null
    var categoryDataBase:MyDataBase? = null
    var budgetDataBase: BudgetDataBase? = null

    override fun onCreate() {
        super.onCreate()
        notificationDataBase = Room.databaseBuilder(this, NotificationDataBase::class.java, "myNotification.db").allowMainThreadQueries().build()
        categoryDataBase = Room.databaseBuilder(this, MyDataBase::class.java, "myCategory.db").allowMainThreadQueries().build()
        budgetDataBase = Room.databaseBuilder(this, BudgetDataBase::class.java, "myBudget.db").allowMainThreadQueries().build()
        instance = this
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt)
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        private var instance: MainApplication? = null
        fun instance() = instance!!
        private const val TAG = "【Firebaseテスト中】"
    }
}