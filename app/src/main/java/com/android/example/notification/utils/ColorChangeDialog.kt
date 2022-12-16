package com.android.example.notification.utils

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.*
import com.android.example.notification.R
import com.android.example.notification.ui.category.ColorChangeGridViewAdapter


class ColorChangeDialog {
    private var colorChangeTv : TextView? = null

    private var mChangeColorClickListener: OnChangeColorClickListener? = null
    private var colors = intArrayOf(
        R.color.gray, R.color.purple_500, R.color.dark_blue, R.color.other_blue,R.color.bright_blue,
        R.color.green, R.color.grass_green, R.color.light_green, R.color.yellow, R.color.chart_orange,
        R.color.dark_orange, R.color.red, R.color.brown,R.color.purple,R.color.gray_blue,
        R.color.green_001, R.color.brown_001, R.color.blue_001,R.color.purple_001,R.color.brown_002
   )
    private val listItem: ArrayList<Map<String, Any>> = ArrayList()
    fun createDeleteDialog(context: Context?): Dialog? {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_change_color, null)
        val gridView = v.findViewById<View>(R.id.grid) as GridView

        for (i in colors.indices) {
            val map: MutableMap<String, Any> = HashMap()
            map["colors"] = colors[i]
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
        gridView.onItemClickListener = object :AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                changeColorDialog.dismiss()
            }
        }
        return changeColorDialog
    }

    fun changeColor(view:View,position: Int,dialog: Dialog?) {

        colorChangeTv?.setOnClickListener{
            mChangeColorClickListener?.onChangeColorClick(view,position)
            closeDialog(dialog)
        }
    }
    /**
     * dialogを閉じる
     *
     * @param mDialogUtils
     */
    private fun closeDialog(mDialogUtils: Dialog?) {
        if (mDialogUtils != null && mDialogUtils.isShowing) {
            mDialogUtils.dismiss()
        }
    }

    open fun setChangeColorClickListener(listener: OnChangeColorClickListener) {
        mChangeColorClickListener = listener
    }

    interface OnChangeColorClickListener {
        fun onChangeColorClick(view:View,Position: Int)
    }


}