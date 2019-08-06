package com.kakaopay.kakaopaypretest.view.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.kakaopay.kakaopaypretest.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {

    lateinit var detailBinding: ActivityDetailBinding
    val detailViewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = DataBindingUtil.setContentView(this, com.kakaopay.kakaopaypretest.R.layout.activity_detail)
        initBind()
        initView()
    }

    private fun initBind() {
        val imageURL = intent.getStringExtra("url") as String
        detailViewModel.setLiveDataImageURL(imageURL)
        detailBinding.vm = detailViewModel
        detailBinding.activity = this
        detailBinding.lifecycleOwner = this
    }

    private fun initView() {
    }

    fun finishActivity(view: View) {
        finish()
    }


}



