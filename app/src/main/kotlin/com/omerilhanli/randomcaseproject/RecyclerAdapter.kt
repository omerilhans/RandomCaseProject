package com.omerilhanli.randomcaseproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private var list: ArrayList<String>? = null) : RecyclerView.Adapter<RecyclerAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        with(parent) {
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_view, this, false)
                .also {
                    return VH(it)
                }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        list
            ?.get(position)
            ?.let {
                holder.tvItemContent?.text = it
            }
    }

    override fun getItemCount() = list?.size ?: 0

    fun update(newList: ArrayList<String>? = null) {
        newList
            ?.apply {
                list = this
            }.also {
                notifyDataSetChanged()
            }
    }

    fun add(item: String? = null) {
        item
            ?.apply {
                list?.add(this)
            }.also {
                notifyDataSetChanged()
            }
    }

    fun clear() {
        list
            ?.clear()
            ?.also {
                notifyDataSetChanged()
            }

    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItemContent: TextView? = null

        init {
            tvItemContent = itemView.findViewById(R.id.tvItemContent)
        }
    }
}