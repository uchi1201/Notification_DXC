package com.android.example.notification.utils

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.android.example.notification.R


class LoadingDialogUtils {

    fun createLoadingDialog(context: Context?, msg: String?): Dialog? {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_loading, null)
        val layout = v
            .findViewById<View>(R.id.dialog_loading_view) as LinearLayout
        val tipTextView = v.findViewById<View>(R.id.tipTextView) as TextView
        tipTextView.text = msg // 设置加载信息
        val loadingDialog = Dialog(context!!, R.style.MyDialogStyle)
        loadingDialog.setCancelable(true)
        loadingDialog.setCanceledOnTouchOutside(false)
        loadingDialog.setContentView(
            layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
        val window = loadingDialog.window
        val lp = window!!.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setGravity(Gravity.CENTER)
        window.attributes = lp
        window.setWindowAnimations(R.style.PopWindowAnimStyle)
        loadingDialog.show()
        return loadingDialog
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