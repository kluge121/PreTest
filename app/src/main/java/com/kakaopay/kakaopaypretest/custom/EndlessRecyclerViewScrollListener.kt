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

        //현재 RecylcerView에 Attach 되어있는, 즉 현재 보이는 아이템 수
        visibleItemCount = layoutManager.childCount

        //어댑터의 getItemCount를 통해 반환되는 전체 아이템 수
        totalItemCount = layoutManager.itemCount

        //첫번째로 보이는 아이템의 포지션, 즉 이 아이템의 포지션 숫자 - 1 만큼의 아이템이 있다는 것
        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

        // 현재 보이는 아이템 숫자 + 위에서 이미 존재하는 아이템 >= 전체아이템
        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
            loadMore()
        }


    }

    abstract fun loadMore()
}

