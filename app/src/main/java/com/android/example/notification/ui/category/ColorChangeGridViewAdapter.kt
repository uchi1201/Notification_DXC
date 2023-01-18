package com.android.example.notification.ui.category

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.toColorInt
import com.android.example.notification.R

/**
 *色変更で出るポップアップのグリッド線アダプター
 * @property mList ArrayList<Map<String, String>>
 * @property mContext Context?
 * @property mGv GridView
 * @constructor
 */
class ColorChangeGridViewAdapter(context: Context?,gridView: GridView, list:ArrayList<Map<String, String>>): BaseAdapter() {
    var mList = list
    private val mContext = context
    var mGv: GridView = gridView

    /**
     * 表示の色の数量
     * @return Int
     */
    override fun getCount(): Int {
        return mList.size
    }

    /**
     * Itemのデータ設定
     * @param position Int
     * @return Map<String, String>
     */
    override fun getItem(position: Int): Map<String, String> {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * ItemのView表示
     * @param position Int
     * @param convertView View
     * @param parent ViewGroup
     * @return View?
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var c1 = LayoutInflater.from(mContext).inflate(R.layout.item_color_layout, null)
        //色のサイズ設定
            val param = AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                mGv.height / 5
            )
        c1.layoutParams = param
        val colorTextView = c1.findViewById(R.id.color_tv) as TextView
        //色の設定
        mContext?.let { mList[position]["colors"]?.toColorInt()
            ?.let { it1 -> colorTextView.setBackgroundColor(it1) } }
        return c1
    }


}
