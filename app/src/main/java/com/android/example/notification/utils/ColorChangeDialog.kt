package com.android.example.notification.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.*
import com.android.example.notification.R
import com.android.example.notification.room.data.CategoryData
import com.android.example.notification.ui.category.ColorChangeGridViewAdapter


class ColorChangeDialog {

    private var mChangeColorClickListener: OnChangeColorClickListener? = null

    private val listItem: ArrayList<Map<String, String>> = ArrayList()

    private fun getRandomColors(colors: ArrayList<String>):ArrayList<String>{
        var colorsList:ArrayList<String> = ArrayList()
        var colorUtils = ColorUtils()
        for (i in 1..20) {
            //20個のランダム色値を取得
            var color = colorUtils.getRandColor()
            //取得した色値が存在する場合は再取得
            while (colors.contains(color)) {
                color = colorUtils.getRandColor()
            }
            //色値が追加
            colorsList.add(color)
        }
        return colorsList
    }

    fun createColorDialog(context: Context?,colors: ArrayList<String>): Dialog? {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_change_color, null)
        val gridView = v.findViewById<View>(R.id.grid) as GridView
        //ランダム色値を取得
        val colorsList = getRandomColors(colors)
        for (i in colorsList.indices) {
            val map: MutableMap<String, String> = HashMap()
            map["colors"] = colorsList[i]
            listItem.add(map)
        }
        val adapter = ColorChangeGridViewAdapter(context, gridView,listItem)
        gridView.adapter = adapter

        val changeColorDialog = Dialog(context!!)
        changeColorDialog.setCancelable(true)
        changeColorDialog.setCanceledOnTouchOutside(true)
        changeColorDialog.setContentView(
            v, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
        val window = changeColorDialog.window
        val lp = window!!.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setGravity(Gravity.CENTER)
        window.attributes = lp
        window.setWindowAnimations(R.style.PopWindowAnimStyle)
        changeColorDialog.show()
        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { _, view, position, _ ->
                changeColorDialog.dismiss()
                if (view != null) {
                    changeColor(context,view,position,adapter)
                }
            }
        return changeColorDialog
    }

    private fun changeColor(context: Context?,view:View, position: Int,adapter: ColorChangeGridViewAdapter) {
        mChangeColorClickListener?.onChangeColorClick(view,position,adapter)


    }


    open fun setChangeColorClickListener(listener: OnChangeColorClickListener) {
        mChangeColorClickListener = listener
    }

    interface OnChangeColorClickListener {
        fun onChangeColorClick(view:View,Position: Int, adapter: ColorChangeGridViewAdapter)
    }


}