package com.android.example.notification.ui.budget.edit


import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import com.android.example.notification.MainApplication
import com.android.example.notification.R
import com.android.example.notification.room.data.BudgetTableData
import com.android.example.notification.ui.base.list.BaseRecycleViewAdapter
import com.android.example.notification.utils.DeleteDialog


class BudgetEditListViewAdapter(context: Context, layoutResourceId: Int, items: ArrayList<BudgetTableData>, init: (View, BudgetTableData) -> Unit) :
    BaseRecycleViewAdapter<BudgetTableData>(layoutResourceId, items, init)  {
    private var animation: Animation? = null
    private val  mContext = context
    private var mDeleteDialog: Dialog? = null
    val budgetDao = MainApplication.instance().budgetDataBase?.budgetDao()
    val colors = intArrayOf(0xa9a9a9, 0xFFFFFF)

    override fun onBindViewHolder(holder: BaseViewHolder<BudgetTableData>, position: Int) {
        holder.bindHolder(items[position])
        //行ごとに背景色を変更
        val colorPos = position % colors.size
        val itemLL = holder.itemView.findViewById<LinearLayout>(R.id.item_ll)
        itemLL.setBackgroundColor(colors[colorPos])
        //[-]削除イベント
        var deleteImg =  holder.itemView.findViewById<ImageView>(R.id.delete_img)
        deleteImg.setOnClickListener {
            deleteItem(it, position)
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
                items.removeAt(position)
                notifyDataSetChanged()
            }
        })
    }
}
