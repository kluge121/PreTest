package com.kakaopay.kakaopaypretest.util

import android.graphics.drawable.Drawable
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kakaopay.kakaopaypretest.*
import com.kakaopay.kakaopaypretest.constant.LoadingState
import com.kakaopay.kakaopaypretest.model.SearchResult
import com.kakaopay.kakaopaypretest.view.detail.DetailViewModel
import com.kakaopay.kakaopaypretest.view.main.MainRecyclerViewAdapter


//main RecyclerView Adapter item , 의존성 주입 대체?
//검색 결과의 리스트 RecyclerView Adapter list에 제공
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

//main DataBinding Glide
//검색결과 이미지뷰 바인딩
@BindingAdapter("glideImageUrl")
fun ImageView.loadImage(imageUrl: String) {
    GlideApp.with(this.context)
            .load(imageUrl)
            .thumbnail(GlideApp.with(this.context)
                    .load(imageUrl)
                    .override(200, 200))
            .centerCrop()
            .override(200, 200)
            .into(this)
}

//main TextView search do it!
//메인 상태 메시지 바인딩
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


//main progress toggle
//메인 Progress 바인딩
//@BindingAdapter("progress")
//fun ProgressBar.toggle(state: LoadingState) {
//    if (state == LoadingState.LOADING) {
//        this.visibility = VISIBLE
//    } else if (state == LoadingState.WAIT || state == LoadingState.NETWORK_ERROR || state == LoadingState.NOT_FOUND) {
//        this.visibility = GONE
//    }
//
//}


//detail DataBinding Glide
//상세보기 ImageView 바인딩
@BindingAdapter(value = ["originalImageUrl", "vm"])
fun ImageView.originalImageUrl(imageUrl: String, vm: DetailViewModel) {
    GlideApp.with(this.context)
            .load(imageUrl)
            .thumbnail(GlideApp.with(this.context)
                    .load(imageUrl)
                    .override(200, 200))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    vm.setStateProgressEnd()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    vm.setStateProgressEnd()
                    return false
                }
            })

            .into(this)
}

// progress toggle
// Progress
@BindingAdapter("progress")
fun ProgressBar.detailToggle(state: LoadingState) {
    if (state == LoadingState.LOADING) {
        this.visibility = VISIBLE
    } else if (state == LoadingState.WAIT || state == LoadingState.NETWORK_ERROR || state == LoadingState.NOT_FOUND) {
        this.visibility = GONE
    }
}





