package com.android.example.notification.ui.budget.edit


import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.android.example.notification.MainApplication
import com.android.example.notification.R
import com.android.example.notification.room.data.BudgetTableData
import com.android.example.notification.room.data.CategoryData
import com.android.example.notification.ui.base.list.BaseRecycleViewAdapter
import com.android.example.notification.utils.DeleteDialog


class BudgetEditListViewAdapter(context: Context, layoutResourceId: Int, items: ArrayList<BudgetTableData>, init: (View, BudgetTableData) -> Unit,textView:TextView) :
    BaseRecycleViewAdapter<BudgetTableData>(layoutResourceId, items, init)  {
    private var animation: Animation? = null
    private val  mContext = context
    private var mDeleteDialog: Dialog? = null
    val budgetDao = MainApplication.instance().budgetDataBase?.budgetDao()
    val colors = intArrayOf(0xa9a9a9, 0xFFFFFF)
    private var budgetTotal = 0
    //予算総額TextView
    var budgetTotalView = textView

    override fun onBindViewHolder(holder: BaseViewHolder<BudgetTableData>, position: Int) {
        var dataList = budgetDao?.getAll()
        dataList?.get(position)?.let { holder.bindHolder(it) }
        //行ごとに背景色を変更
        val colorPos = position % colors.size
        val itemLL = holder.itemView.findViewById<LinearLayout>(R.id.item_ll)
        itemLL.setBackgroundColor(colors[colorPos])
        //[-]削除イベント
        var deleteImg =  holder.itemView.findViewById<ImageView>(R.id.delete_img)
        deleteImg.setOnClickListener {
            deleteItem(it, position)
        }
        budgetTotal += dataList?.get(position)?.budgetTotal!!
        budgetTotalView.text = budgetTotal.toString()
        //budget編集
        var budgetEdit = holder.itemView.findViewById<EditText>(R.id.budget_edt)
        budgetEdit.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                budgetTotal -= if(budgetEdit.text.toString().isNotEmpty()){
                    budgetEdit.text.toString().toInt()
                } else {
                    0
                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Todo

            }

            override fun afterTextChanged(s: Editable?) {
                budgetTotal += if(budgetEdit.text.toString().isNotEmpty()){
                    budgetEdit.text.toString().toInt()
                } else {
                    0
                }
                budgetTotalView.text = budgetTotal.toString()
                //更新DBデータ処理
                var budgetEditData = budgetEdit.text.toString()
                if(budgetEditData.isEmpty()){
                    budgetEditData = "0"
                }
                var budgetTableData =BudgetTableData( items[position].category,items[position].budget,budgetEditData.toInt())
                budgetDao?.update(budgetTableData)
            }

        })


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
                budgetTotal = 0
                items.removeAt(position)
                notifyDataSetChanged()
            }
        })
    }

    fun addCategoryData(categoryData: BudgetTableData){
        budgetTotal = 0
        items.add(categoryData)
        budgetDao?.insert(categoryData)

    }

}
