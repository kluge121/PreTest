package com.kakaopay.kakaopaypretest.util

import android.graphics.Bitmap
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
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.kakaopay.kakaopaypretest.R
import com.kakaopay.kakaopaypretest.constant.LoadingState
import com.kakaopay.core.entity.SearchResultEntity
import com.kakaopay.kakaopaypretest.view.detail.DetailViewModel
import com.kakaopay.kakaopaypretest.view.main.MainRecyclerViewAdapter
import java.net.URLDecoder


//검색 결과의 리스트 RecyclerView Adapter list에 제공
@BindingAdapter("items")
fun RecyclerView.bindingItem(resultEntity: com.kakaopay.core.entity.SearchResultEntity) {
    if (resultEntity.documents.size > 0) {
        if (this.adapter != null) {
            (this.adapter as MainRecyclerViewAdapter).run {
                replaceAll(resultEntity.documents)
                notifyDataSetChanged()
            }
        }
    } else {
        (this.adapter as MainRecyclerViewAdapter).run {
            replaceAll(resultEntity.documents)
            notifyDataSetChanged()
        }
    }
}

//검색결과 이미지뷰 바인딩
@BindingAdapter("glideImageUrl")
fun ImageView.loadImage(imageUrl: String) {
    GlideApp.with(this.context)
        .load(imageUrl)
        .thumbnail(
            GlideApp.with(this.context)
                .load(imageUrl)
                .override(200, 200)
        )
        .centerCrop()
        .override(200, 200)
        .into(this)
}

//메인 상태 메시지 바인딩
@BindingAdapter(value = ["resultEntity", "progress"])
fun TextView.imageCheck(resultEntity: com.kakaopay.core.entity.SearchResultEntity, state: LoadingState) {
    if (resultEntity.documents.size == 0 && state == LoadingState.WAIT) {
        this.text = context.getString(R.string.search_do_it)
        this.visibility = VISIBLE
    } else if (resultEntity.documents.size == 0 && state == LoadingState.NOT_FOUND) {
        this.text = context.getString(R.string.not_found)
        this.visibility = VISIBLE
    } else if (state == LoadingState.LOADING) {
        this.visibility = GONE
    } else if (state == LoadingState.NETWORK_ERROR) {
        this.text = context.getString(R.string.network_error)
        this.visibility = VISIBLE
    }
}

//상세보기 ImageView 바인딩
@BindingAdapter(value = ["originalImageUrl", "vm"])
fun ImageView.originalImageUrl(imageUrl: String, vm: DetailViewModel) {

    val decodeURL = URLDecoder.decode(imageUrl, "UTF-8").replace("&","%26")

    GlideApp.with(this.context).asBitmap()
        .load(decodeURL)
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                vm.setStateNotExit()
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                vm.setStateWait()
                return false
            }
        })
        .into(object : BitmapImageViewTarget(this) {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                super.onResourceReady(resource, transition)
                vm.setBitmap(resource)
            }
        })
}


// progress toggle 설정
@BindingAdapter("progress")
fun ProgressBar.detailToggle(state: LoadingState) {
    if (state == LoadingState.LOADING) {
        this.visibility = VISIBLE
    } else if (state == LoadingState.WAIT || state == LoadingState.NETWORK_ERROR ||
        state == LoadingState.NOT_FOUND || state == LoadingState.SUCCESS || state == LoadingState.NOT_EXIST
    ) {
        this.visibility = GONE
    }
}





