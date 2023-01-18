package com.android.example.notification.ui.budget.edit


import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.example.notification.MainApplication
import com.android.example.notification.R
import com.android.example.notification.room.data.BudgetTableData
import com.android.example.notification.ui.base.list.BaseRecycleViewAdapter
import com.android.example.notification.utils.DeleteDialog

/**
 * 予算編集リストのアダプター
 * @property animation Animation?
 * @property mContext Context
 * @property mDeleteDialog Dialog?　
 * @property budgetDao BudgetDao?
 * @property colors IntArray
 * @property budgetTotal Int
 * @property budgetTotalView TextView
 * @property isSame Boolean
 * @constructor
 */
class BudgetEditListViewAdapter(context: Context, layoutResourceId: Int, items: ArrayList<BudgetTableData>, init: (View, BudgetTableData) -> Unit,textView:TextView) :
    BaseRecycleViewAdapter<BudgetTableData>(layoutResourceId, items, init)  {
    //ダイアログを表示時の動画
    private var animation: Animation? = null
    private val  mContext = context
    //削除確認ダイアログ
    private var mDeleteDialog: Dialog? = null
    //DBのDaoを取得して、テーブルに操作用
    val budgetDao = MainApplication.instance().budgetDataBase?.budgetDao()
    //リストのItemのBackground色, 1行おきにグレー表示
    val colors = intArrayOf(0xa9a9a9, 0xFFFFFF)
    //予算総額
    private var budgetTotal = 0
    //予算総額TextView
    var budgetTotalView = textView
    //選択したカテゴリーが既に存在するかフラグ判断
    private var isSame = false

    override fun onBindViewHolder(holder: BaseViewHolder<BudgetTableData>, position: Int) {
        try {
            //DBから全体データを検索
            var dataList = budgetDao?.getAll()
            //検索したデータをItemに表示設定
            dataList?.get(position)?.let { holder.bindHolder(it) }

            //行ごとに背景色を変更(0xa9a9a9, 0xFFFFFF)
            val colorPos = position % colors.size
            val itemLL = holder.itemView.findViewById<LinearLayout>(R.id.item_ll)
            itemLL.setBackgroundColor(colors[colorPos])

            //[-]削除イベント
            var deleteImg =  holder.itemView.findViewById<ImageView>(R.id.delete_img)
            deleteImg.setOnClickListener {
                deleteItem(it, position)
            }
            //予算総額計算
            budgetTotal += dataList?.get(position)?.budgetTotal!!
            //SharedPreferencesにデータを保存
            MainApplication.instance().saveData(mContext,"totalBudgetValue",budgetTotal)
            //予算総額設定、SharedPreferencesからtotalBudgetValue　Keyによりデータを取得
            budgetTotalView.text = MainApplication.instance().getData(mContext,"totalBudgetValue")
            //リスト中budget編集
            var budgetEdit = holder.itemView.findViewById<EditText>(R.id.budget_edt)
            budgetEdit.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    //計算総額設定、編集入力前、計算総額は該当の入力ボックスの値を減算する
                    budgetTotal -= if(budgetEdit.text.toString().isNotEmpty()){
                        budgetEdit.text.toString().toInt()
                    } else {
                        //該当の入力ボックスの値がなし場合、計算総額は０を減算する
                        0
                    }

                }
                //Edittextの値が変化中に処理
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //Todo

                }

                override fun afterTextChanged(s: Editable?) {
                    //該当入力ボックス編集したあと、計算総額を計算する
                    budgetTotal += if(budgetEdit.text.toString().isNotEmpty()){
                        budgetEdit.text.toString().toInt()
                    } else {
                        //該当の入力ボックスの値がなし場合、計算総額は０をPlusする
                        0
                    }
                    //計算総額TextView設定
                    MainApplication.instance().saveData(mContext,"totalBudgetValue",budgetTotal)
                    budgetTotalView.text = MainApplication.instance().getData(mContext,"totalBudgetValue")
                    //更新DBデータ処理
                    var budgetEditData = budgetEdit.text.toString()
                    //入力ボックスが何もない場合、budgetEditDataを0に設定ため、DBデータを更新
                    if(budgetEditData.isEmpty()){
                        budgetEditData = "0"
                    }
                    //DBデータを更新
                    var budgetTableData =BudgetTableData( items[position].category,items[position].budget,budgetEditData.toInt())
                    budgetDao?.update(budgetTableData)
                }

            })

        } catch (e:Exception){
            Toast.makeText(mContext,"エラーを発生しまいした、別のカテゴリーを選択してください。",Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * 「ー」を押下して削除処理
     * @param view View
     * @param position Int
     */
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
                //リストItemから該当データをRemove
                items.removeAt(position)
                //最新リスト更新
                notifyDataSetChanged()
            }
        })
    }

    fun addCategoryData(categoryData: BudgetTableData){
        budgetTotal = 0
        //DBから予算リストを検索
        val budget = budgetDao?.getAll()
        //検索したデータが追加したいデータと合わせて、存在したら、エラートースト提示
        if (budget != null) {
            for (item in budget){
               if(item.category == categoryData.category) {
                   isSame = true
                   break
               } else {
                   isSame = false
               }
            }
        }
        //追加したいデータが存在しない場合、リスト表示とDBを更新
        if(!isSame){
            items.add(categoryData)
            budgetDao?.insert(categoryData)
            notifyDataSetChanged()
        } else {
            //エラー提示
            Toast.makeText(mContext,"カテゴリーが既に存在しています、別のカテゴリーを選択してください。",Toast.LENGTH_SHORT).show()
        }

    }

}
