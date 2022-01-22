package com.example.redditappandroid.models

import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class ResyclerViewPaginator(recyclerView: RecyclerView): RecyclerView.OnScrollListener() {

    private val batchSize: Long = 5

     var lastItemName: String = ""
        set(value) {
            field = value
        }
     var isLoaded :Boolean = false
        set(value){
            field = value
        }

    private val threshold = 3
    private val layoutManager: RecyclerView.LayoutManager?

    init {
        recyclerView.addOnScrollListener(this)
        this.layoutManager = recyclerView.layoutManager
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == SCROLL_STATE_IDLE) {
            val totalItemCount = layoutManager!!.itemCount

            var firstVisibleItemPosition = 0
            if (layoutManager is LinearLayoutManager) {
                firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            } else if (layoutManager is GridLayoutManager) {
                firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }

            if(firstVisibleItemPosition + threshold >= totalItemCount){
                if(isLoaded==false){
                    isLoaded = true
                    loadMore(lastItemName, batchSize)
                }else{
                    isLoaded=false
                }
            }
        }
    }

    private fun reset() {
        lastItemName = ""
    }

    abstract fun loadMore(start: String, count: Long)
}