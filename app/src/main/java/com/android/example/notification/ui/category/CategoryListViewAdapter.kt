package com.android.example.notification.ui.category


import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.example.notification.R
import com.android.example.notification.data.DataX
import com.example.practice.base.list.BaseRecycleViewAdapter
import kotlin.math.abs


class CategoryListViewAdapter(context: Context, layoutResourceId: Int, items: List<DataX>, init: (View, DataX) -> Unit) :
    BaseRecycleViewAdapter<DataX>(layoutResourceId, items, init) {
    private var downX:Float=0.0f
    private var upX:Float=0.0f
    private var view: View? = null
    private var deleteTv: TextView? = null
    private var animation: Animation? = null
    private val  mContext = context

        override fun onBindViewHolder(holder: BaseViewHolder<DataX>, position: Int) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.push_out)
            holder.bindHolder(items[position])
            holder.itemView.setOnTouchListener OnTouchListener@{ v, event ->  // 为每个item设置setOnTouchListener事件
                val delBtn = holder.itemView.findViewById<TextView>(R.id.tv_item_delete)
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downX = event.x // 获取手指x坐标
                        deleteTv?.visibility = View.GONE

                    }
                    MotionEvent.ACTION_UP -> upX = event.x // 获取x坐标值
                }
                if (delBtn != null) {
                    if (abs(downX - upX) > 80 && upX < downX) { //向左滑动，删除item
                        delBtn.visibility = View.VISIBLE // 显示删除button
                        deleteTv = delBtn
                        view = v // 得到itemview，在上面加动画
                        return@OnTouchListener true // 终止事件
                    }
                    if (abs(downX - upX) > 80 && upX > downX) { //撤销删除操作
                        if (delBtn.visibility === View.VISIBLE) { //此时Button可见
                            delBtn.visibility = View.GONE
                        }
                        return@OnTouchListener true // 终止事件
                    }
                    return@OnTouchListener false // 释放事件，使onitemClick可以执行
                }
                false
            }
            val delBtn = holder.itemView.findViewById<TextView>(R.id.tv_item_delete)
            delBtn.setOnClickListener {
                // 为button绑定事件

                    if (deleteTv != null) {
                        deleteTv!!.visibility = View.GONE // 点击删除按钮后，影藏按钮
                        view?.let { it1 -> deleteItem(it1, position) } // 删除数据，加动画
                    }

            }

        }
    private fun deleteItem(view: View, position: Int) {

        view.startAnimation(animation) // 给view设置动画
        animation?.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) { // 动画执行完毕
                items.drop(position) // 把数据源里面相应数据删除
                notifyDataSetChanged()
            }
        })
    }
    }
