package com.android.example.notification.utils

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.android.example.notification.R

class FilterDialog {

    private lateinit var dateStartSpList: Array<String>
    private lateinit var dateEndSpList: Array<String>
    private lateinit var typesSpList: Array<String>
    private lateinit var categorySpList: Array<String>
    var settingButton : Button? = null


    fun createDialog(index:Array<Map<String, Int>>,context: Context,themeResId: Int): Dialog{
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_custom_filter, null)
        val layout = v.findViewById<View>(R.id.dialog_custom_filter_view) as LinearLayout
        val dateStartSp = v.findViewById<View>(R.id.date_start) as Spinner
        val dateEndSp =  v.findViewById<View>(R.id.date_end) as Spinner
        settingButton = v.findViewById<View>(R.id.setting_btn) as Button
        val typesSp =  v.findViewById<View>(R.id.types_sp) as Spinner
        val categorySp =  v.findViewById<View>(R.id.category_sp) as Spinner

        dateStartSpList = getDataStartList(context)
        //期間の起始時間のアダプター
        var dateStartAdapter: ArrayAdapter<String> = ArrayAdapter(context,android.R.layout.simple_list_item_1,dateStartSpList)
        //配列アダプタのレイアウトスタイルを設定する
        dateStartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //ドロップダウンボックスの配列アダプタの設定
        dateStartSp.adapter = dateStartAdapter
        //ドロップダウン・ボックスのデフォルトの表示の最初の項目の設定
        index[0]["dateStart"]?.let { dateStartSp.setSelection(it) }
        //期間の結束時間のアダプター
        dateEndSpList = emptyArray()
        var dateEndAdapter: ArrayAdapter<String> = ArrayAdapter(context,android.R.layout.simple_list_item_1,dateEndSpList)
        //配列アダプタのレイアウトスタイルを設定する
        dateEndAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //ドロップダウンボックスの配列アダプタの設定
        dateEndSp.adapter = dateEndAdapter
        //ドロップダウン・ボックスのデフォルトの表示の最初の項目の設定
        index[1]["dateEnd"]?.let { dateEndSp.setSelection(it) }
        //期間の種類のアダプター
        typesSpList = emptyArray()
        var typesSpAdapter: ArrayAdapter<String> = ArrayAdapter(context,android.R.layout.simple_list_item_1,typesSpList)
        //配列アダプタのレイアウトスタイルを設定する
        typesSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //ドロップダウンボックスの配列アダプタの設定
        typesSp.adapter = typesSpAdapter
        //ドロップダウン・ボックスのデフォルトの表示の最初の項目の設定
        index[2]["type"]?.let { typesSp.setSelection(it) }
        //期間のカテゴリーのアダプター
        categorySpList = emptyArray()
        var categorySpAdapter: ArrayAdapter<String> = ArrayAdapter(context,android.R.layout.simple_list_item_1,categorySpList)
        //配列アダプタのレイアウトスタイルを設定する
        categorySpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //ドロップダウンボックスの配列アダプタの設定
        categorySp.adapter = categorySpAdapter
        //ドロップダウン・ボックスのデフォルトの表示の最初の項目の設定
        index[3]["category"]?.let { typesSp.setSelection(it) }


        val dialog = Dialog(context!!,themeResId)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(
            layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
        val window = dialog.window
        val lp = window!!.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setGravity(Gravity.CENTER)
        window.attributes = lp
        window.setWindowAnimations(R.style.PopWindowAnimStyle)
        dialog.show()
        return dialog
    }

    private fun getDataStartList(context: Context): Array<String> {
        //Todo サーバー側からもらうか
        //一旦仮データ
        return context.resources.getStringArray(R.array.frequency)
    }

    fun settingInfo(context: Context,dialog: Dialog?){
        settingButton?.setOnClickListener {
            //SharedPreferencesインスタンスを取得 sp_filter：ファイル名
            val sp: SharedPreferences = context.getSharedPreferences("sp_filter", Context.MODE_PRIVATE)
            //Editorインスタンスを取得
            val editor = sp.edit()
            //key-valueによりデータを保存
//            editor.putInt("dateStartIndex",freqIndex)
//            editor.putInt("freqIndexSub",freqIndexSub)
            //apply()は非同期書き込みデータです
            editor.apply()
            closeDialog(dialog)
        }
    }
    /**
     * dialogを閉じる
     *
     * @param mDialogUtils
     */
    fun closeDialog(mDialogUtils: Dialog?) {
        if (mDialogUtils != null && mDialogUtils.isShowing) {
            mDialogUtils.dismiss()
        }
    }
}