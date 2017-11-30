package com.profir.delegatedrecyclerview

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by profiralexandr on 29/11/2017.
 */

open class DelegatedRecyclerView<T : Any, I : ViewHolderDelegate<T>, V : ViewHolder<T, I>> : RecyclerView {
    var viewHolderFactory: (parent: ViewGroup, viewType: Int, delegate: I) -> V
        set(value) {
            adapterCompat.viewHolderFactory = value
        }
        get() {
            return adapterCompat.viewHolderFactory
        }

    var delegate: I
        set(value) {
            adapterCompat.delegate = value
        }
        get() {
            return adapterCompat.delegate
        }

    var adapterCompat: com.profir.delegatedrecyclerview.Adapter<T, I, V>
        get() {
            return adapter as com.profir.delegatedrecyclerview.Adapter<T, I, V>
        }
        set(value) {
            adapter = value
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        layoutManager = LinearLayoutManager(context)
        adapterCompat = Adapter<T, I, V>()
    }

    fun setItems(items: List<T>) {
        adapterCompat.items.clear()
        adapterCompat.items.addAll(items)
        adapterCompat.notifyDataSetChanged()
    }

    fun addItems(items: List<T>) {
        val currentSize = adapterCompat.items.size
        adapterCompat.items.addAll(items)
        adapterCompat.notifyItemRangeInserted(currentSize, items.size)
    }

    /**
     * Other methods to manipulate data inside adapter and notify the changes correctly
     */
}

open class Adapter<T : Any, I : ViewHolderDelegate<T>, V : ViewHolder<T, I>> : RecyclerView.Adapter<V>() {
    val items: MutableList<T> = ArrayList()
    lateinit var viewHolderFactory: (parent: ViewGroup, viewType: Int, delegate: I) -> V
    lateinit var delegate: I

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V =
            viewHolderFactory(parent, viewType, delegate)

    override fun onBindViewHolder(holder: V, position: Int) {
        val item = items[position]
        holder.bindViewHolder(item)
    }

    override fun getItemCount(): Int = items.size
}

abstract class ViewHolder<T : Any, I : ViewHolderDelegate<T>> : RecyclerView.ViewHolder {
    val delegate: I
    lateinit var item: T

    constructor(view: View, delegate: I) : super(view) {
        this.delegate = delegate
        itemView.setOnClickListener { delegate.onItemSelected(item) }
    }

    fun bindViewHolder(item: T) {
        this.item = item
        bindViews(item)
    }

    abstract fun bindViews(item: T)
}

interface ViewHolderDelegate<in T> {
    fun onItemSelected(item: T): Boolean = false
}
