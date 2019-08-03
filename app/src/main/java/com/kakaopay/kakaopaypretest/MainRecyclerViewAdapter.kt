package com.kakaopay.kakaopaypretest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kakaopay.kakaopaypretest.databinding.ItemMainImageBinding


class MainRecyclerViewAdapter<T>() : RecyclerView.Adapter<BaseImageViewHolder<T>>() {

    private val items = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseImageViewHolder<T> {

        val binding =
                ItemMainImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NormalImageViewHolder(binding)

    }

    override fun onBindViewHolder(holder: BaseImageViewHolder<T>, position: Int) {
        if (holder is NormalImageViewHolder) {
            holder.bindView(items[position])
        }
    }

    override fun getItemCount() = items.size

    fun replaceAll(newItem: MutableList<T>) {
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