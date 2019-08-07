package com.kakaopay.kakaopaypretest.custom

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener() {

    var pastVisiblesItems = 0
    var visibleItemCount = 0
    var totalItemCount = 0


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as GridLayoutManager

        visibleItemCount = layoutManager.childCount
        totalItemCount = layoutManager.itemCount
        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
            loadMore()
        }


    }

    abstract fun loadMore()
}

