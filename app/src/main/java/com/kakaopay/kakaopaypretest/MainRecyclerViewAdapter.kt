package com.kakaopay.kakaopaypretest

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kakaopay.kakaopaypretest.databinding.ItemMainImageBinding


class MainRecyclerViewAdapter(var screenWidthSize: Int) : RecyclerView.Adapter<BaseImageViewHolder<ImageItem>>() {

    private val items = mutableListOf<ImageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseImageViewHolder<ImageItem> {
        val binding: ItemMainImageBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_main_image, parent, false)

        val param = binding.root.layoutParams
        param.width = screenWidthSize / 3
        param.height = screenWidthSize / 3
        binding.root.layoutParams = param
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
        outRect.set(R.dimen.item_space_size, R.dimen.item_space_size, R.dimen.item_space_size, R.dimen.item_space_size)
    }
}
