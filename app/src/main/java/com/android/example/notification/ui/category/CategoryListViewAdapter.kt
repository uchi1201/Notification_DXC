package com.android.example.notification.ui.category


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.android.example.notification.R
import com.android.example.notification.room.MyDataBase
import com.android.example.notification.room.data.CategoryData
import com.android.example.notification.ui.base.list.BaseRecycleViewAdapter
import com.android.example.notification.utils.ColorChangeDialog
import com.android.example.notification.utils.DeleteDialog
import kotlin.math.abs


class CategoryListViewAdapter(context: Context, dataBase: MyDataBase?,
                              layoutResourceId: Int, items: ArrayList<CategoryData>, init: (View, CategoryData) -> Unit) :
    BaseRecycleViewAdapter<CategoryData>(layoutResourceId, items, init)  {
    private var downX:Float=0.0f
    private var upX:Float=0.0f
    private var view: View? = null
    private var deleteTv: TextView? = null

    private var animation: Animation? = null
    private val  mContext = context
    private var mDeleteDialog: Dialog? = null
    private val  mDataBase =  dataBase
    val categoryDao = mDataBase?.categoryDao()


    override fun onBindViewHolder(holder: BaseViewHolder<CategoryData>, position: Int) {
        holder.bindHolder(items[position])
       var colorList: ArrayList<String> = arrayListOf()
        for(i in items.indices){
            //既にリストに選択された色を取得
            colorList.add(items[i].color)
        }
        var colorChange =  holder.itemView.findViewById<TextView>(R.id.color_tv)
        colorChange?.setOnClickListener{
            //色選択ダイアログを表示（選択中のリストPosition、既に存在色リスト）
            colorChangeDialogShow(position,colorList)
        }
        val delBtn = holder.itemView.findViewById<TextView>(R.id.tv_item_delete)
        holder.itemView.setOnTouchListener OnTouchListener@{ v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    //指x座標の取得
                    downX = event.x
                    deleteTv?.visibility = View.GONE
                }

                MotionEvent.ACTION_UP -> upX = event.x //x座標値の取得
            }
            if (delBtn != null) {
                //左にスライドしてitemを削除
                if (abs(downX - upX) > 80 && upX < downX) {
                    //削除buttonを表示
                    delBtn.visibility = View.VISIBLE
                    deleteTv = delBtn
                    //itemviewを手に入れ、そこに動画を加える
                    view = v
                    return@OnTouchListener true //終了イベント
                }
                //削除操作を元に戻す
                if (abs(downX - upX) > 80 && upX > downX) {
                    if (delBtn.visibility === View.VISIBLE) {
                        delBtn.visibility = View.GONE
                    }
                    return@OnTouchListener true //終了イベント
                }
                return@OnTouchListener false //onitemClickが実行できるようにイベントを解放する
            }
            false
        }
        delBtn.setOnClickListener {
                if (deleteTv != null) {
                    //削除ボタンをクリックすると、ボタンを隠す
                    deleteTv!!.visibility = View.GONE
                    //データを削除し、アニメーションを追加
                    view?.let { it1 -> deleteItem(it1, position) }
                }

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
                categoryDao?.delete(items[position])
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

    private fun colorChangeDialogShow(colorPosition:Int,colorList: ArrayList<String>){
        var colorChangeDialog = ColorChangeDialog()
        //ダイアログを作成
        colorChangeDialog.createColorDialog(mContext,colorList)
        //選択された色のCallback
        colorChangeDialog.setChangeColorClickListener(object :
            ColorChangeDialog.OnChangeColorClickListener {
                override fun onChangeColorClick(view:View,position: Int,adapter: ColorChangeGridViewAdapter){
                    //選択された色はリスト表示画面へ戻って色を変更
                    val item = adapter.getItem(position)
                    val colorString = item["colors"]
                    if (colorString != null) {
                        //該当リストアイテムの色を変更
                        items[colorPosition].color=colorString
                        //DBデータを更新
                        categoryDao?.updateColorForCategoryData(colorString,items[colorPosition].category)
                    }
                    //リストへ反映
                    notifyDataSetChanged()
                }
        })
    }

    fun setCategoryData(categoryData: CategoryData){
        items.add(categoryData)
        categoryDao?.insert(categoryData)
        notifyDataSetChanged()
    }
}
