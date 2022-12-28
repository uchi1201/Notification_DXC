package com.android.example.notification.ui.base.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecycleViewAdapter<T>(private val layoutResourceId: Int, val items: ArrayList<T>, val init: (View, T) -> Unit) :
    RecyclerView.Adapter<BaseRecycleViewAdapter.BaseViewHolder<T>>() {
    private var monItemClickListener: OnRecyclerItemClickListener? = null
    private var itemClick: T.() -> Unit = {}

    interface OnRecyclerItemClickListener {
        fun onRecyclerItemClick(view:View,Position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val itemView = LayoutInflater.from(parent?.context).inflate(layoutResourceId, parent, false)
        return BaseViewHolder(itemView, init)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindHolder(items[items.size-1-position])
        holder.itemView.setOnClickListener {
            monItemClickListener?.onRecyclerItemClick(holder.itemView,items.size-1-position)
        }
    }

    open fun setRecyclerItemClickListener(listener: OnRecyclerItemClickListener) {
        monItemClickListener = listener
    }
    override fun getItemCount() = items.size

    class BaseViewHolder<T>(view: View, val init: (View, T) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bindHolder(item: T) {
            init(itemView, item)
        }
    }

}
