package com.kakaopay.kakaopaypretest

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("items")
fun RecyclerView.bindingItem(result: MutableLiveData<SearchResult>) {

    if (this.adapter != null) {
        (this.adapter as MainRecyclerViewAdapter).replaceAll(result.value!!.documents)
    }
}


//DataBinding Glide
@BindingAdapter("glideImageUrl")
fun ImageView.loadImage(imageUrl: String) {
    GlideApp.with(this.context)
            .load(imageUrl)
            .into(this)
}