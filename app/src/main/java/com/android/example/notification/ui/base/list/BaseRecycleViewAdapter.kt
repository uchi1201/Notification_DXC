package com.example.practice.base.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.base.list.BaseRecycleViewAdapter.OnRecyclerItemClickListener


abstract class BaseRecycleViewAdapter<T>(val layoutResourceId: Int, val items: List<T>, val init: (View, T) -> Unit) :
    RecyclerView.Adapter<BaseRecycleViewAdapter.BaseViewHolder<T>>() {
    private var monItemClickListener: OnRecyclerItemClickListener? = null
    private var itemClick: T.() -> Unit = {}

    interface OnRecyclerItemClickListener {
        fun onRecyclerItemClick(view:View,Position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecycleViewAdapter.BaseViewHolder<T> {
        val itemView = LayoutInflater.from(parent?.context).inflate(layoutResourceId, parent, false)
        return BaseViewHolder(itemView, init)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindHolder(items[position])
        holder.itemView.setOnClickListener {
            monItemClickListener?.onRecyclerItemClick(holder.itemView,position)
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
