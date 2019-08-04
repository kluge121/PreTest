package com.kakaopay.kakaopaypretest

import android.widget.ImageView
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