package com.android.example.notification.utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.example.notification.MainApplication
import com.android.example.notification.R


class CustomDialog(context: Context?, themeResId: Int):CommonDialog(context,themeResId) {
    var itemSelectionListener:  AdapterView.OnItemSelectedListener? = null
    var spinnerAdapter:ArrayAdapter<String>? =null

     fun createDialog(themeResId: Int, frequencylist: Array<String>,frequencylistSub: Array<String>): Dialog{
        super.createDialog(themeResId)

        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_custom_frequency, null)
        val layout = v
            .findViewById<View>(R.id.dialog_custom_view) as LinearLayout
        val frequencySub = v.findViewById<View>(R.id.frequency_sub) as Spinner
        val frequency =  v.findViewById<View>(R.id.frequency_spinner) as Spinner

        var frequencyAdapter: ArrayAdapter<String> = ArrayAdapter(context,android.R.layout.simple_spinner_item,frequencylist)
        //配列アダプタのレイアウトスタイルを設定する
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //ドロップダウンボックスの配列アダプタの設定
        frequency.adapter = frequencyAdapter
        //ドロップダウン・ボックスのデフォルトの表示の最初の項目の設定
        frequency.setSelection(0)
        //ドロップダウンリストの配列アダプタを宣言する
        var frequencySubAdapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item,frequencylistSub)
        //配列アダプタのレイアウトスタイルを設定する
        frequencySubAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //ドロップダウンボックスの配列アダプタの設定
        frequencySub.adapter = frequencySubAdapter
        //ドロップダウン・ボックスのデフォルトの表示の最初の項目の設定
        frequencySub.setSelection(0)

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