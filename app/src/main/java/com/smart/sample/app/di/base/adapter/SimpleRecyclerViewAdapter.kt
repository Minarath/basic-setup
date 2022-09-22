package com.smart.sample.app.di.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.smart.sample.app.BR
import com.smart.sample.app.di.base.adapter.SimpleRecyclerViewAdapter.SimpleViewHolder
import java.util.*

class SimpleRecyclerViewAdapter<M, B : ViewDataBinding> :
    RecyclerView.Adapter<SimpleViewHolder<B>> {
    @LayoutRes
    private val layoutResId: Int
    private val modelVariableId: Int
    private var callback: SimpleCallback<B, M>? = null
    private val dataList: MutableList<M> = ArrayList()

    fun removeItem(i: Int) {
        try {
            if (i != -1) {
                dataList.removeAt(i)
                notifyItemRemoved(i)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    operator fun set(i: Int, scanResult: M) {
        if (scanResult == null) return
        dataList.add(i, scanResult)
        notifyItemChanged(i)
    }

    interface SimpleCallback<B : ViewDataBinding, M> {
        fun onItemClick(v: View, m: M) {}
        fun onItemClick(v: View, m: M, pos: Int) {}
        fun onPositionClick(v: View, pos: Int) {}
        fun onViewCreated(holder: SimpleViewHolder<B>, position: Int, m: M) {}
    }

    constructor(@LayoutRes layoutResId: Int, modelVariableId: Int, callback: SimpleCallback<B,M>) {
        this.layoutResId = layoutResId
        this.modelVariableId = modelVariableId
        this.callback = callback
    }

    constructor(@LayoutRes layoutResId: Int, modelVariableId: Int) {
        this.layoutResId = layoutResId
        this.modelVariableId = modelVariableId
    }

    fun setCallback(callback: SimpleCallback<B, M>) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder<B> {
        val binding: B =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutResId, parent, false)
        if (callback != null) binding.setVariable(BR.callback, callback)
        return SimpleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder<B>, position: Int) {
        holder.binding.setVariable(modelVariableId, dataList[position])
        holder.binding.setVariable(BR.holder, holder)
        callback?.onViewCreated(holder, position, dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setDummyList(size: Int, seed: M) {
        dataList.clear()
        var i = 0
        while (i < size) {
            dataList.add(seed)
            i++
        }
        notifyDataSetChanged()
    }

    var list: List<M>
        get() = dataList
        set(newDataList) {
            dataList.clear()
            dataList.addAll(newDataList)
            notifyDataSetChanged()
        }

    fun addToList(newDataList: List<M>) {
        val positionStart = dataList.size
        val itemCount = newDataList.size
        dataList.addAll(newDataList)
        notifyItemRangeInserted(positionStart, itemCount)
    }

    fun clearList() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun addData(data: M) {
        val positionStart = dataList.size
        dataList.add(data)
        notifyItemInserted(positionStart)
    }

    fun addData(position: Int, data: M) {
        val positionStart = dataList.size
        dataList.add(position, data)
        notifyItemInserted(positionStart)
    }


    class SimpleViewHolder<S : ViewDataBinding>(val binding: S) : RecyclerView.ViewHolder(
        binding.root
    )


}