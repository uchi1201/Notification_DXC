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


class CustomDialog{

    private lateinit var frequencyList: Array<String>
    private lateinit var frequencyListSub: Array<String>
    var settingButton : Button? = null
    var freqIndex: Int = 0
    var freqIndexSub: Int = 0
    private var freqSpinnerSub: Spinner? = null

    fun createDialog(frequencyIndex:Int,frequencySubIndex:Int,context: Context,themeResId: Int): Dialog{
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_custom_frequency, null)
        val layout = v.findViewById<View>(R.id.dialog_custom_view) as LinearLayout
        freqSpinnerSub = v.findViewById<View>(R.id.frequency_sub) as Spinner
        val frequency =  v.findViewById<View>(R.id.frequency_spinner) as Spinner
        settingButton = v.findViewById<View>(R.id.setting_btn) as Button
        val timePicker =  v.findViewById<View>(R.id.time_picker) as TimePicker
         timePicker.setIs24HourView(true)
         frequencyList = context.resources.getStringArray(R.array.frequency)
         frequencyListSub = emptyArray()
        var frequencyAdapter: ArrayAdapter<String> = ArrayAdapter(context,android.R.layout.simple_list_item_1,frequencyList)
        //配列アダプタのレイアウトスタイルを設定する
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //ドロップダウンボックスの配列アダプタの設定
        frequency.adapter = frequencyAdapter
        //ドロップダウン・ボックスのデフォルトの表示の最初の項目の設定
        frequency.setSelection(frequencyIndex)
        frequency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: AdapterView<*>?, view: View, pos: Int, id: Long) {
                 if(freqIndex != pos ){
                     frequencySubCreate(context,pos,0)
                 } else {
                     frequencySubCreate(context,pos,frequencySubIndex)
                 }
                 freqIndex = pos

             }
             override fun onNothingSelected(parent: AdapterView<*>?) {
                 //Todo
             }
         }

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

    /**
     * 毎月の時日付、毎週の時曜日、毎日の時選択不可
     * @param context Context
     * @param position Int
     * @param frequencySubIndex Int
     */
    private fun  frequencySubCreate(context: Context,position:Int,frequencySubIndex:Int){
        frequencyListSub = when(position){
            0-> context.resources.getStringArray(R.array.day)
            1-> context.resources.getStringArray(R.array.week)
            else-> emptyArray()
        }
        //ドロップダウンリストの配列アダプタを宣言する
        var frequencySubAdapter: ArrayAdapter<String> = ArrayAdapter(context,android.R.layout.simple_spinner_item,frequencyListSub)
        //配列アダプタのレイアウトスタイルを設定する
        frequencySubAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //ドロップダウンボックスの配列アダプタの設定
        freqSpinnerSub?.adapter = frequencySubAdapter
        //ドロップダウン・ボックスのデフォルトの表示の最初の項目の設定
        freqSpinnerSub?.setSelection(frequencySubIndex)
        freqSpinnerSub?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, pos: Int, id: Long) {
                freqIndexSub = pos
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Todo
            }
        }
    }

    /**
     * 設定した毎月の時日付、毎週の時曜日、毎日の時などを保存
     * @param context Context
     * @param dialog Dialog?
     */
    fun settingInfo(context: Context,dialog: Dialog?){
        settingButton?.setOnClickListener {
            //SharedPreferencesインスタンスを取得 sp_name：ファイル名
            val sp: SharedPreferences = context.getSharedPreferences("sp_name", Context.MODE_PRIVATE)
            //Editorインスタンスを取得
            val editor = sp.edit()
            //key-valueによりデータを保存
            editor.putInt("freqIndex",freqIndex)
            editor.putInt("freqIndexSub",freqIndexSub)
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