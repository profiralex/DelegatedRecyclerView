package com.profir.sample

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.profir.delegatedrecyclerview.DelegatedRecyclerView
import com.profir.delegatedrecyclerview.ViewHolder
import com.profir.delegatedrecyclerview.ViewHolderDelegate
import kotlinx.android.synthetic.main.activity_sample_item.view.*

/**
 * Created by profiralexandr on 30/11/2017.
 */

class SampleDelegatedRV : DelegatedRecyclerView<SampleItemModel, SampleDelegate, SampleViewHolder> {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)
}

class SampleViewHolder(view: View, delegate: SampleDelegate) : ViewHolder<SampleItemModel, SampleDelegate>(view, delegate) {

    init {
        itemView.minusBT.setOnClickListener {
            if (delegate.onItemDecremented(item)) {
                bindViews(item)
            }
        }
        itemView.plusBT.setOnClickListener {
            if (delegate.onItemIncremented(item)) {
                bindViews(item)
            }
        }
    }

    override fun bindViews(item: SampleItemModel) {
        itemView.countTV.text = item.count.toString()
    }
}

interface SampleDelegate : ViewHolderDelegate<SampleItemModel> {
    fun onItemIncremented(item: SampleItemModel): Boolean = false

    fun onItemDecremented(item: SampleItemModel): Boolean = false
}

data class SampleItemModel(val id: Int) {
    var count: Int = 0
}
