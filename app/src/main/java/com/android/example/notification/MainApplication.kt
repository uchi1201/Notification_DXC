package com.android.example.notification

import android.app.Application

class MainApplication: Application()  {

    var spinnerMonth:String = "1"

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null
        fun instance() = instance!!
    }
}