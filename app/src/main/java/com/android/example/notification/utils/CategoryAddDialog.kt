package com.android.example.notification.utils

import android.app.AlertDialog
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

    fun createAddCategoryDialog(context: Context?,colorsList: ArrayList<String>): Dialog? {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_category_add, null)
        val layout = v
            .findViewById<View>(R.id.dialog_category_add_view) as LinearLayout
        addButton = v.findViewById<View>(R.id.add_btn) as Button
        categoryEdt = v.findViewById<View>(R.id.category_edt) as EditText
        colorLl = v.findViewById<View>(R.id.color_change_ll) as LinearLayout
        colorText = v.findViewById<View>(R.id.color_change) as TextView


        colorLl?.setOnClickListener {
            //登録の色の押下イベント処理、色選択ダイアログを表示
            colorChange(context,colorsList)
        }

        val addCategoryDaoDialog = Dialog(context!!)
        addCategoryDaoDialog.setCancelable(true)
        addCategoryDaoDialog.setCanceledOnTouchOutside(true)
        addCategoryDaoDialog.setContentView(
            layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
        //登録ボタンのイベント処理
        addButton?.setOnClickListener {
            //該当入力したカテゴリーと色を取得
            var a = categoryEdt!!.text.toString()
            var b = colorString.toString()
            categoryData = CategoryData(a, b)
            //入力したカテゴリーと色は登録ボタンのCallback、カテゴリー管理画面へ戻ってデータを処理
            mAddCategoryButtonClickListener?.onAddCategoryButtonClick(categoryData)
            addCategoryDaoDialog.dismiss()
        }
        //初期ダイアログを表示時、色の表示は色変更で出るポップアップの第一個色値
        val colorInit = getInitColorString(colorsList)
        if(colorInit != "0") {
            //色変更で出るポップアップがデータがある場合、色の表示
            colorText?.setBackgroundColor(colorInit.toColorInt())
            //下記のcolorStringは登録ボタンを押下時、該当選択色を取得設定
            val colorTemp = colorText?.background as ColorDrawable
            colorString = "#" + colorTemp.color.toHexString()
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


    private fun colorChange(context: Context?,colors: ArrayList<String>){
        var colorChangeDialog = ColorChangeDialog()
        //TextView色を押下して、色変更で出るポップアップを表示
        colorChangeDialog.createColorDialog(context,colors)
        //色変更で出るポップアップの色を選択した後、TextView色の変更処理
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

    private fun getInitColorString(colorsList: ArrayList<String>): String{
        var colors:ArrayList<String>? = ArrayList()
        for (item in ColorChangeDialog().totalColors) {
            if (!colorsList.contains(item)) {
                colors?.add(item)
            }
        }
        var result = if(colors.isNullOrEmpty()){
            "0"
        } else {
            colors[0]
        }
        return result
    }
    open fun setAddCategoryButtonClickListener(listener: OnAddCategoryButtonClickListener) {
        mAddCategoryButtonClickListener = listener
    }

    interface OnAddCategoryButtonClickListener {
        fun onAddCategoryButtonClick(categoryData: CategoryData?)
    }

}