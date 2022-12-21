package com.android.example.notification.utils

import android.graphics.Color
import java.util.*

class ColorUtils {
    fun getRandomColorInt(): Int {
        val random = Random()
        val red = random.nextInt(200)
        val green = random.nextInt(200)
        val blue = random.nextInt(200)
        return Color.rgb(red, green, blue)
    }

    fun getRandColor(): String {
        var R: String
        var G: String
        var B: String
        val random = Random()
        R = Integer.toHexString(random.nextInt(256)).uppercase(Locale.getDefault())
        G = Integer.toHexString(random.nextInt(256)).uppercase(Locale.getDefault())
        B = Integer.toHexString(random.nextInt(256)).uppercase(Locale.getDefault())
        R = if (R.length == 1) "0$R" else R
        G = if (G.length == 1) "0$G" else G
        B = if (B.length == 1) "0$B" else B
        return "#$R$G$B"
    }
}