package com.android.example.notification.data

/**
 * 円グラフ描画用
 * @property data PieGraphData
 * @constructor
 */
data class PieDataValueBean(val data: PieGraphData)
data class PieGraphData(
    val name:String,
    val budget: Float
)