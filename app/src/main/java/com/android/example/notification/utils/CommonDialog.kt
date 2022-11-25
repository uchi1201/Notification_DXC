package com.android.example.notification.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button

open class CommonDialog(context: Context?, themeResId: Int) : Dialog(context!!, themeResId) {

    open fun createDialog(themeResId: Int): CommonDialog {
        val dialog = CommonDialog(context,
            themeResId)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }

    class Builder (private val context: Context) {
        private var text1: String? = null
        private var text2: String? = null
        private var text3: String? = null
        private var contentView: View? = null
        private var color: Int = 0
        private var settingBtn: Button? = null
        private var settingBtnListener: OnClickListener? = null
        private var settingContext: String? = null

        fun setText1(text: String): Builder {
            this.text1 = text
            return this
        }
        fun setText2(text: String): Builder {
            this.text2 = text
            return this
        }
        fun setText3(text: String): Builder {
            this.text3 = text
            return this
        }

        fun settingBtn(text: String,onClickListener: OnClickListener): Builder {
            this.settingContext = text
            this.settingBtnListener = onClickListener
            return this
        }

        fun setContentView(v: View): Builder {
            this.contentView = v
            return this
        }



    }
}