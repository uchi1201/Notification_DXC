package com.android.example.notification.utils

import android.app.Dialog
import android.content.Context
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.android.example.notification.R
import com.android.example.notification.data.DataX
import java.text.FieldPosition


class DeleteDialog {
    private var deleteButton : Button? = null
    var cancelButton : Button? = null
    fun createDeleteDialog(context: Context?): Dialog? {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_delete, null)
        val layout = v
            .findViewById<View>(R.id.dialog_delete_view) as LinearLayout
        deleteButton = v.findViewById<View>(R.id.delete_btn) as Button
        cancelButton = v.findViewById<View>(R.id.cancel_btn) as Button
        val deleteDialog = Dialog(context!!)
        deleteDialog.setCancelable(true)
        deleteDialog.setCanceledOnTouchOutside(false)
        deleteDialog.setContentView(
            layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
        val window = deleteDialog.window
        val lp = window!!.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setGravity(Gravity.CENTER)
        window.attributes = lp
        window.setWindowAnimations(R.style.PopWindowAnimStyle)
        deleteDialog.show()
        return deleteDialog
    }

    fun delete(context: Context?,view:View,position: Int,items: ArrayList<DataX>,dialog: Dialog?): MutableLiveData<Boolean> {
        var result = MutableLiveData<Boolean> ()

        deleteButton?.setOnClickListener{
            result.value = true
            closeDialog(dialog)
        }
        cancelButton?.setOnClickListener{
            closeDialog(dialog)
            result.value = false
        }
        return result
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
}