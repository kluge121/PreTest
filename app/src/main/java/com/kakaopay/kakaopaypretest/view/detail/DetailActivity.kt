package com.kakaopay.kakaopaypretest.view.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.kakaopay.kakaopaypretest.custom.BaseActivity
import com.kakaopay.kakaopaypretest.databinding.ActivityDetailBinding


class DetailActivity : BaseActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = DataBindingUtil.setContentView(this, com.kakaopay.kakaopaypretest.R.layout.activity_detail)
        initBind()
        initView()
    }

    override fun initBind() {
        val imageURL = intent.getStringExtra("url") as String
        detailViewModel.setLiveDataImageURL(imageURL)
        detailBinding.vm = detailViewModel
        detailBinding.activity = this
        detailBinding.lifecycleOwner = this
    }

    override fun initView() {
    }

    fun finishActivity(view: View) {
        finish()
    }


}



