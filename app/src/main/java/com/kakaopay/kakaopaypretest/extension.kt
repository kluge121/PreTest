package com.kakaopay.kakaopaypretest

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView


//@BindingAdapter("items")
//fun RecyclerView.bindingItem(result: MutableLiveData<SearchResult>) {
//    Log.e("바인딩어댑터","items")
//    Log.e("바인딩어댑터","${result.value}")
//    if (this.adapter != null) {
//        (this.adapter as MainRecyclerViewAdapter).replaceAll(result.value!!.documents)
//    }
//}

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
            .into(this)

}