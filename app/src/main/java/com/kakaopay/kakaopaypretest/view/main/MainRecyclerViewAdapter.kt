package com.kakaopay.kakaopaypretest.view.main

import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakaopay.kakaopaypretest.BR
import com.kakaopay.kakaopaypretest.R
import com.kakaopay.kakaopaypretest.constant.MAIN_GRID_COLUMN
import com.kakaopay.kakaopaypretest.databinding.ItemMainImageBinding
import com.kakaopay.kakaopaypretest.model.ImageItem
import com.kakaopay.kakaopaypretest.view.detail.DetailActivity


class MainRecyclerViewAdapter(screenWidthSize: Int) : RecyclerView.Adapter<BaseImageViewHolder<ImageItem>>() {

    private val items = mutableListOf<ImageItem>()
    private val itemSize = screenWidthSize / MAIN_GRID_COLUMN


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseImageViewHolder<ImageItem> {

        val binding: ItemMainImageBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_main_image, parent, false)

        //하나의 아이템 크기 = 디바이스 가로 길이를 MAIN_GRID_COLUMN 로 나눈 값 (현재 = 3)
        val param = (binding.root as ViewGroup).getChildAt(0).layoutParams
        param.height = itemSize
        param.width = itemSize
        (binding.root as ViewGroup).getChildAt(0).layoutParams = param
        return NormalImageViewHolder(binding)

    }

    override fun onBindViewHolder(holder: BaseImageViewHolder<ImageItem>, position: Int) {
        if (holder is NormalImageViewHolder) {
            holder.bindView(items[position])
        }
    }

    override fun getItemCount() = items.size

    fun replaceAll(newItem: MutableList<ImageItem>) {
        items.clear()
        items.addAll(newItem)
    }

    override fun getItemId(position: Int): Long {
        return items[position].image_url.hashCode().toLong()
    }
}

abstract class BaseImageViewHolder<T>(binding: ItemMainImageBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bindView(item: T)
}

class NormalImageViewHolder<T>(var binding: ItemMainImageBinding) : BaseImageViewHolder<T>(binding) {
    override fun bindView(item: T) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.holder, this)
    }

    fun showDetailImage(view: View) {
        val url = (binding.item as ImageItem).image_url
        val intent = Intent(binding.root.context, DetailActivity::class.java)
        intent.apply {
            putExtra("url", url)
            putExtra("position", adapterPosition)
        }
        binding.root.context.startActivity(intent)
    }
}

class ItemSpaceDecoration : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val edgeSpace = view.context.resources.getDimensionPixelOffset(R.dimen.item_edge_space_size)
        val position = parent.getChildAdapterPosition(view)
        val lp = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndex = lp.spanIndex

        if (spanIndex == 0) {
            outRect.left = edgeSpace
        } else if (spanIndex == 1) {
            outRect.right = edgeSpace
            outRect.left = edgeSpace
        } else if (spanIndex == 2) {
            outRect.right = edgeSpace
        }

        if (position == 0 || position == 1 || position == 2) {
            outRect.top = edgeSpace
            outRect.bottom = edgeSpace
        } else {
            outRect.bottom = edgeSpace
        }

    }
}
