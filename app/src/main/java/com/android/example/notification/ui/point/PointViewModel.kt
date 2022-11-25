package com.android.example.notification.ui.point

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.example.notification.R
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

class PointViewModel(application: Application): AndroidViewModel(application) {
    private val appContext = application.applicationContext

    private val _text = MutableLiveData<String>().apply {
        value = "This is point Fragment"
    }
    val text: LiveData<String> = _text

    private val _barData= MutableLiveData<BarData>().apply {
        value = createBarData()
    }
    val barData: LiveData<BarData> = _barData

    private fun createBarData(): BarData {
        //表示用サンプルデータの作成
        val x = listOf<Float>(6f, 7f, 8f, 9f, 10f, 11f)//X軸データ, 現在を12月として過去6ヶ月？
        val y1 = x.map { it }//Y軸データ1（X軸の1乗）
        val y2 = x.map { it * it }//Y軸データ2（X軸の2乗）

        //①Entryにデータ格納
        var entryList = mutableListOf<BarEntry>()
        for (i in x.indices) {
            entryList.add(
                BarEntry(x[i], floatArrayOf(y1[i], y2[i]))
            )
        }

        //BarDataSetのリスト
        val barDataSets = mutableListOf<IBarDataSet>()

        //②DataSetにデータ格納
        val barDataSet = BarDataSet(entryList, "linear")

        //③DataSetのフォーマット指定
        val color1 = appContext.resources.getColor(R.color.chart_blue, null)
        val color2 = appContext.resources.getColor(R.color.chart_orange, null)
        barDataSet.colors = listOf(color1, color2)

        //リストに格納
        barDataSets.add(barDataSet)

        //④BarDataにBarDataSet格納して返却
        return BarData(barDataSets)
    }
}
