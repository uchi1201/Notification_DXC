package com.android.example.notification.json

import android.content.Context
import android.util.Log
import com.android.example.notification.network.base.BaseNetworkApi.Companion.TAG
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class ReadJsonFile(var fileName: String) {
    fun <T> getCategoryListData(context: Context,classOfT: Class<T>):T? {
        var gson = Gson()
        var `is`: InputStream? = null
        var bos: ByteArrayOutputStream? = null
        try {
            `is` = context.assets.open(fileName)
            bos = ByteArrayOutputStream()
            val bytes = ByteArray(4 * 1024)
            var len = 0
            while (`is`.read(bytes).also { len = it } != -1) {
                bos.write(bytes, 0, len)
            }
            val json = String(bos.toByteArray())

            return gson.fromJson(json, classOfT)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                `is`?.close()
                bos?.close()
            } catch (e: IOException) {
                Log.e(TAG, "getStates", e)
            }
        }
        return null
    }
}