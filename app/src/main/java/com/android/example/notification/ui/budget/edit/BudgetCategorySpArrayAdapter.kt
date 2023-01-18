package com.android.example.notification.ui.budget.edit

import android.R
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * カテゴリープルダウンリストのアダプター
 * @param T
 * @property arrayList List<T>?
 * @property resourceId Int
 * @property isInput Boolean
 * @constructor
 */
class BudgetCategorySpArrayAdapter<T>
    (context: Context?, resource: Int, objects: List<T>?) :
    ArrayAdapter<Any?>(context!!, resource, objects!!) {
    private val arrayList = objects
    private var resourceId = resource
    //Hintとカテゴリー名入力するかフラグ
    var isInput = false

    /**
     * 戻るカテゴリーのSpinner　Item表示数量
     * @return Int
     */
    override fun getCount(): Int {
        //最後のHint「カテゴリ名を選択」はリストに表示しない
        val count = super.getCount()
        return if (count > 0) count - 1 else count
    }

    /**
     * Spinner itemのView表示
     * @param position Int
     * @param convertView View?
     * @param parent ViewGroup
     * @return View
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertView = LayoutInflater.from(parent?.context).inflate(resourceId, parent, false)
        val tv = convertView!!.findViewById(R.id.text1) as TextView
        /*
        Hint「カテゴリ名を選択」の文字、文字表示サイズ、文字色表示
         */
        if(position == arrayList?.size?.minus(1)) {
            tv.text = arrayList[position].toString()
            tv.textSize = 15f
            tv.setTextColor(Color.GRAY)
            isInput = false
        } else {
            //Hint以外のカテゴリー表示
            tv.text = arrayList?.get(position).toString()
            tv.textSize = 18f
            tv.setTextColor(Color.BLACK)
            isInput = true
        }
        return convertView


    }
}
