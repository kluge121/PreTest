package com.kakaopay.kakaopaypretest

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


//RecyclerView Adapter item , 의존성 주입 대체?
@BindingAdapter("items")
fun RecyclerView.bindingItem(result: SearchResult) {
    if (result.documents.size > 0) {
        if (this.adapter != null) {
            (this.adapter as MainRecyclerViewAdapter).run {
                replaceAll(result.documents)
                notifyDataSetChanged()
            }
        }
    }
}


//DataBinding Glide
@BindingAdapter("glideImageUrl")
fun ImageView.loadImage(imageUrl: String) {
    GlideApp.with(this.context)
            .load(imageUrl)
            .centerCrop()
            .into(this)
}

//TextView search do it!
@BindingAdapter("imageCheck")
fun TextView.imageCheck(result: SearchResult) {
    if (result.documents.size == 0) {
        this.visibility = View.VISIBLE
    } else if (result.documents.size >= 1) {
        this.visibility = View.GONE
    }
}
