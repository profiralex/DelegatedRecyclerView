package com.profir.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.profir.sample.R.layout.activity_sample
import com.profir.sample.R.layout.activity_sample_item
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_sample)
        initViews()
    }

    private fun initViews() {
        sampleDRV.viewHolderFactory = { parent, _, delegate ->
            val view = layoutInflater.inflate(activity_sample_item, parent, false)
            SampleViewHolder(view, delegate)
        }

        sampleDRV.delegate = object : SampleDelegate {
            val TAG = "SampleDelegate_TAG"

            override fun onItemSelected(item: SampleItemModel): Boolean {
                Log.d(TAG, "$item.id selected")
                return true
            }

            override fun onItemIncremented(item: SampleItemModel): Boolean {
                item.count++
                Log.d(TAG, "$item.id incremented: count = ${item.count}")
                return true
            }

            override fun onItemDecremented(item: SampleItemModel): Boolean {
                item.count--
                Log.d(TAG, "$item.id decremented: count = ${item.count}")
                return true
            }
        }

        sampleDRV.addItems(listOf(
                SampleItemModel(0),
                SampleItemModel(1),
                SampleItemModel(2),
                SampleItemModel(3),
                SampleItemModel(4)))
    }
}
