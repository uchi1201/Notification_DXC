package com.android.example.notification.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.*
import com.android.example.notification.R
import com.android.example.notification.ui.category.ColorChangeGridViewAdapter


class ColorChangeDialog {

    private var mChangeColorClickListener: OnChangeColorClickListener? = null
    private var colors :Array<String> =
        arrayOf("#919191", "#FF6200EE", "#000080", "#00688B","#00EEEE",
        "#05A724", "#5CAF5C", "#A1DA68", "#ffd700", "#ff8c00",
        "#E78B02", "#E70219", "#692D19","#F20FEE","#3C4A5D",
        "#37538B00", "#C05D4B1D", "#3B807A","#50344F","#C0513F19")

    private val listItem: ArrayList<Map<String, String>> = ArrayList()
    fun createColorDialog(context: Context?): Dialog? {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_change_color, null)
        val gridView = v.findViewById<View>(R.id.grid) as GridView

        for (i in colors.indices) {
            val map: MutableMap<String, String> = HashMap()
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
        fun onChangeColorClick(view:View,Position: Int, adapter: ColorChangeGridViewAdapter)
    }


}