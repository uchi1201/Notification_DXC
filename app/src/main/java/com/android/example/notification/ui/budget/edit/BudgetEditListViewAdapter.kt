package com.android.example.notification.ui.budget.edit


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.android.example.notification.MainApplication
import com.android.example.notification.R
import com.android.example.notification.room.MyDataBase
import com.android.example.notification.room.data.BudgetTableData
import com.android.example.notification.room.data.CategoryData
import com.android.example.notification.ui.base.list.BaseRecycleViewAdapter
import com.android.example.notification.utils.ColorChangeDialog
import com.android.example.notification.utils.DeleteDialog
import kotlin.math.abs


class BudgetEditListViewAdapter(context: Context, layoutResourceId: Int, items: ArrayList<BudgetTableData>, init: (View, BudgetTableData) -> Unit) :
    BaseRecycleViewAdapter<BudgetTableData>(layoutResourceId, items, init)  {
    private var view: View? = null
    private var animation: Animation? = null
    private val  mContext = context
    private var mDeleteDialog: Dialog? = null
    val budgetDao = MainApplication.instance().budgetDataBase?.budgetDao()

    override fun onBindViewHolder(holder: BaseViewHolder<BudgetTableData>, position: Int) {
        holder.bindHolder(items[position])
        var deleteImg =  holder.itemView.findViewById<ImageView>(R.id.delete_img)
        deleteImg.setOnClickListener {
            view?.let { it1 -> deleteItem(it1, position) }
        }
    }
    private fun deleteItem(view: View, position: Int) {
        val deleteDialog = DeleteDialog()
        //削除ダイアログを作成
        mDeleteDialog = deleteDialog.createDeleteDialog(mContext)
        animation = AnimationUtils.loadAnimation(mContext, R.anim.push_out)
        //ダイアログ削除処理
        deleteDialog.delete(view,position,mDeleteDialog)
        //削除ボタンのクリックCallback
        deleteDialog.setDeleteButtonClickListener(object :
            DeleteDialog.OnDeleteButtonClickListener {
            override fun onDeleteButtonClick(view:View,Position: Int){
                //DBのデータを削除
                budgetDao?.delete(items[position])
                //viewにアニメーションを設定する
                view.startAnimation(animation)
                animation?.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationRepeat(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {
                        //動画実行完了
                        //リストを更新
                        items.removeAt(position)
                        notifyDataSetChanged()
                    }
                })
            }
        })
    }
}
