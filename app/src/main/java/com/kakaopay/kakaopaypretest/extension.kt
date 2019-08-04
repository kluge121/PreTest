package com.kakaopay.kakaopaypretest

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView



@BindingAdapter("items")
fun RecyclerView.bindingItem(list: LiveData<Any>) {
    this.adapter.let {
        (this.adapter as? MainRecyclerViewAdapter<Any>)?.run {
            replaceAll(list.value as MutableList<Any>)
            notifyDataSetChanged()
        }
    }
}



//DataBinding Glide
@BindingAdapter("glideImageUrl")
fun ImageView.loadImage(imageUrl: String) {
    GlideApp.with(this.context)
        .load(imageUrl)
        .into(this)
}