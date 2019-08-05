package com.kakaopay.kakaopaypretest

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakaopay.kakaopaypretest.databinding.ItemMainImageBinding


class MainRecyclerViewAdapter(screenWidthSize: Int) : RecyclerView.Adapter<BaseImageViewHolder<ImageItem>>() {

    private val items = mutableListOf<ImageItem>()
    private val itemSize = screenWidthSize / 3


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseImageViewHolder<ImageItem> {
        val binding: ItemMainImageBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_main_image, parent, false)

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


}


abstract class BaseImageViewHolder<T>(binding: ItemMainImageBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bindView(item: T)
}

class NormalImageViewHolder<T>(var binding: ItemMainImageBinding) : BaseImageViewHolder<T>(binding) {
    override fun bindView(item: T) {
        binding.setVariable(BR.item, item)
    }

}


class ItemSpaceDecoration : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val edgeSpace = view.context.resources.getDimensionPixelOffset(R.dimen.item_edge_space_size)
        val betweenSpace = view.context.resources.getDimensionPixelOffset(R.dimen.item_between_space_size)

        val position = parent.getChildAdapterPosition(view)
        val lp = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndex = lp.spanIndex



        if(spanIndex == 0){
            outRect.left = edgeSpace
        }else if(spanIndex==1){
            outRect.right = edgeSpace
            outRect.left = edgeSpace
        }else if(spanIndex == 2){
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
