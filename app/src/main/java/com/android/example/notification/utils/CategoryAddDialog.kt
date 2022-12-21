package com.android.example.notification.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.android.example.notification.R
import com.android.example.notification.room.data.CategoryData
import com.android.example.notification.ui.base.list.BaseRecycleViewAdapter
import com.android.example.notification.ui.category.ColorChangeGridViewAdapter
import okhttp3.internal.toHexString
import java.text.FieldPosition


class CategoryAddDialog {
    private var addButton : Button? = null
    private var categoryEdt: EditText? = null
    private var colorText: TextView? = null
    private var colorLl: LinearLayout? = null
    private var mAddCategoryButtonClickListener: OnAddCategoryButtonClickListener? = null
    private var categoryData: CategoryData? = null
    private var colorString : String? = null

    fun createAddCategoryDialog(context: Context?): Dialog? {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_category_add, null)
        val layout = v
            .findViewById<View>(R.id.dialog_category_add_view) as LinearLayout
        addButton = v.findViewById<View>(R.id.add_btn) as Button
        categoryEdt = v.findViewById<View>(R.id.category_edt) as EditText
        colorLl = v.findViewById<View>(R.id.color_change_ll) as LinearLayout
        colorText = v.findViewById<View>(R.id.color_change) as TextView
        colorLl?.setOnClickListener {
            colorChange(context)
        }
        val colorTemp = colorText?.background as ColorDrawable
        colorString = "#" + colorTemp.color.toHexString()
        val addCategoryDaoDialog = Dialog(context!!)
        addCategoryDaoDialog.setCancelable(true)
        addCategoryDaoDialog.setCanceledOnTouchOutside(true)
        addCategoryDaoDialog.setContentView(
            layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )

        addButton?.setOnClickListener {
            var a = categoryEdt!!.text.toString()
            var b = colorString.toString()
            categoryData = CategoryData(a, b)

            mAddCategoryButtonClickListener?.onAddCategoryButtonClick(categoryData)
            addCategoryDaoDialog.dismiss()
        }

        val window = addCategoryDaoDialog.window
        val lp = window!!.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setGravity(Gravity.CENTER)
        window.attributes = lp
        window.setWindowAnimations(R.style.PopWindowAnimStyle)



        addCategoryDaoDialog.show()
        return addCategoryDaoDialog
    }

    private fun addCategory(categoryData: CategoryData?) {
        mAddCategoryButtonClickListener?.onAddCategoryButtonClick(categoryData)
    }

    private fun colorChange(context: Context?){
        var colorChangeDialog = ColorChangeDialog()
        colorChangeDialog.createColorDialog(context)
        colorChangeDialog.setChangeColorClickListener(object :
            ColorChangeDialog.OnChangeColorClickListener {
            override fun onChangeColorClick(view:View,position: Int,adapter: ColorChangeGridViewAdapter){
                val item = adapter.getItem(position)
                colorString = item["colors"]
                val color = colorString?.toColorInt()
                if (color != null) {
                    colorText!!.setBackgroundColor(color)
                }
            }
        })
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

    open fun setAddCategoryButtonClickListener(listener: OnAddCategoryButtonClickListener) {
        mAddCategoryButtonClickListener = listener
    }

    interface OnAddCategoryButtonClickListener {
        fun onAddCategoryButtonClick(categoryData: CategoryData?)
    }

}