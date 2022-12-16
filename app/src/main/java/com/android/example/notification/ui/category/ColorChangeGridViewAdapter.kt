package com.android.example.notification.ui.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import com.android.example.notification.R


class ColorChangeGridViewAdapter(context: Context?,gridView: GridView, list:ArrayList<Map<String, Any>>): BaseAdapter() {
    var mList = list
    private val mContext = context
    var mGv: GridView = gridView

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var c1 = LayoutInflater.from(mContext).inflate(R.layout.item_color_layout, null)
            val param = AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                mGv.height / 5
            )
            c1.layoutParams = param
            val colorTextView = c1.findViewById(R.id.color_tv) as TextView
        mContext?.let { colorTextView.setBackgroundColor(it.getColor(mList[position]["colors"] as Int)) }
        colorTextView.setOnClickListener {

        }
        return c1
    }

}
