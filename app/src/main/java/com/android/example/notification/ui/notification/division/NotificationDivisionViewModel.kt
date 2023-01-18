package com.android.example.notification.ui.notification.division

import androidx.lifecycle.ViewModel

class NotificationDivisionViewModel() : ViewModel() {
    /**
     * カテゴリーSpinnerItemのデータを設定
     * @return ArrayList<String>
     */
    fun getCategoryList(): ArrayList<String>{
        //Todo サーバー側からもらうか
        //一旦仮データ
        var categorySpList: ArrayList<String> = ArrayList()
        categorySpList.add("食費")
        categorySpList.add("水道")
        categorySpList.add("その他")
        return categorySpList
    }


}