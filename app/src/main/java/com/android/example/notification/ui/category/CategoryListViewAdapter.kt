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

/**
 * カテゴリー管理画面のリストアダプター
 * @property downX Float
 * @property upX Float
 * @property view View?
 * @property deleteTv TextView?
 * @property animation Animation?
 * @property mContext Context
 * @property mDeleteDialog Dialog?
 * @property mDataBase MyDataBase?
 * @property categoryDao CategoryDao?
 * @constructor
 */
class CategoryListViewAdapter(context: Context, dataBase: MyDataBase?,
                              layoutResourceId: Int, items: ArrayList<CategoryData>, init: (View, CategoryData) -> Unit) :
    BaseRecycleViewAdapter<CategoryData>(layoutResourceId, items, init)  {
    private var downX:Float=0.0f//削除スワイプの指x座標
    private var upX:Float=0.0f//指を上げる時、削除スワイプの指x座標
    private var view: View? = null
    private var deleteTv: TextView? = null
    private var animation: Animation? = null
    private val  mContext = context
    private var mDeleteDialog: Dialog? = null
    private val  mDataBase =  dataBase
    val categoryDao = mDataBase?.categoryDao()


    override fun onBindViewHolder(holder: BaseViewHolder<CategoryData>, position: Int) {
        //ItemViewのbindHolder、Item表示用
        holder.bindHolder(items[position])
        //現在リストの存在したカテゴリー色
       var colorList: ArrayList<String> = arrayListOf()
        for(i in items.indices){
            //既にリスト中に選択された色を取得設定
            colorList.add(items[i].color)
        }
        //カテゴリー後ろの色
        var colorChange =  holder.itemView.findViewById<TextView>(R.id.color_tv)
        colorChange?.setOnClickListener{
            //色選択ダイアログを表示（選択中のリストPosition、既に存在色リスト）
            colorChangeDialogShow(position,colorList)
        }
        //スワイプ時表示した削除ボタン
        val delBtn = holder.itemView.findViewById<TextView>(R.id.tv_item_delete)
        //Itemのtouch
        holder.itemView.setOnTouchListener OnTouchListener@{ v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    //指x座標
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

    /**
     * 削除確認ダイアログ
     * @param view View
     * @param position Int　選択Itemの位置
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
                categoryDao?.delete(items[position])
                //viewにアニメーションを設定する
                view.startAnimation(animation)
                animation?.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationRepeat(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {
                        //動画実行完了
                        //リストの該当ItemをRemove
                        items.removeAt(position)
                        //リスト更新
                        notifyDataSetChanged()
                    }
                })
            }
        })
    }

    /**
     *　色変更で出るポップアップ
     * @param colorPosition Int
     * @param colorList ArrayList<String>　リスト中に既に存在の色リスト
     */
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
                    //ポップアップ中選択した色値を設定
                    val colorString = item["colors"]
                    if (colorString != null) {
                        //該当リストアイテムの色を変更
                        items[colorPosition].color=colorString
                        //変更した色がDBデータに更新
                        categoryDao?.updateColorForCategoryData(colorString,items[colorPosition].category)
                    }
                    //リストへ反映
                    notifyDataSetChanged()
                }
        })
    }

    /**
     * カテゴリーに対して色を設定
     * @param categoryData CategoryData
     */
    fun setCategoryData(categoryData: CategoryData){
        //リストItemを追加
        items.add(categoryData)
        //DBに追加
        categoryDao?.insert(categoryData)
        notifyDataSetChanged()
    }
}
