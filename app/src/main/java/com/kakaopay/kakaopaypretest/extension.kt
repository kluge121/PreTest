package com.kakaopay.kakaopaypretest

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
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
    } else {
        (this.adapter as MainRecyclerViewAdapter).run {
            replaceAll(result.documents)
            notifyDataSetChanged()
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
@BindingAdapter(value = ["result", "progress"])
fun TextView.imageCheck(result: SearchResult, state: LoadingState) {
    if (result.documents.size == 0 && state == LoadingState.WAIT) {
        this.text = context.getString(R.string.search_do_it)
        this.visibility = VISIBLE
    } else if (result.documents.size == 0 && state == LoadingState.NOT_FOUND) {
        this.text = context.getString(R.string.not_found)
        this.visibility = VISIBLE
    } else if (state == LoadingState.LOADING) {
        this.visibility = GONE
    } else if (state == LoadingState.NETWORK_ERROR) {
        this.text = context.getString(R.string.network_error)
        this.visibility = VISIBLE
    }
}


@BindingAdapter("progress")
fun ProgressBar.toggle(state: LoadingState) {
    if (state == LoadingState.LOADING) {
        this.visibility = VISIBLE
    } else if (state == LoadingState.WAIT || state == LoadingState.NETWORK_ERROR || state == LoadingState.NOT_FOUND) {
        this.visibility = GONE
    }

}
