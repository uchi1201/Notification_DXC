package com.android.example.notification.ui.budget.edit

import android.R
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class BudgetCategorySpArrayAdapter<T>
    (context: Context?, resource: Int, objects: List<T>?) :
    ArrayAdapter<Any?>(context!!, resource, objects!!) {
    private val arrayList = objects
    private var resourceId = resource
    var isInput = false

    override fun getCount(): Int {
        // don't display last item. It is used as hint.
        val count = super.getCount()
        return if (count > 0) count - 1 else count
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertView = LayoutInflater.from(parent?.context).inflate(resourceId, parent, false)
        val tv = convertView!!.findViewById(R.id.text1) as TextView
        if(position == arrayList?.size?.minus(1)) {
            tv.text = arrayList[position].toString()
            tv.textSize = 15f
            tv.setTextColor(Color.GRAY)
            isInput = false
        } else {
            tv.text = arrayList?.get(position).toString()
            tv.textSize = 18f
            tv.setTextColor(Color.BLACK)
            isInput = true
        }
        return convertView


    }
}
