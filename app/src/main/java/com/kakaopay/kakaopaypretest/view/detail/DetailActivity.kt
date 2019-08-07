package com.kakaopay.kakaopaypretest.view.detail

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kakaopay.kakaopaypretest.R
import com.kakaopay.kakaopaypretest.constant.LoadingState
import com.kakaopay.kakaopaypretest.custom.BaseActivity
import com.kakaopay.kakaopaypretest.databinding.ActivityDetailBinding


class DetailActivity : BaseActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory(application))
                .get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        initBind()
        initView()
    }

    override fun initBind() {
        val imageURL = intent.getStringExtra("url") as String
        detailViewModel.setLiveDataImageURL(imageURL)
        detailBinding.vm = detailViewModel
        detailBinding.activity = this
        detailBinding.lifecycleOwner = this
        detailViewModel.state.observe(this, Observer {
            if (it == LoadingState.SUCCESS) {
                showToast(getString(R.string.image_save_success))
            } else if (it == LoadingState.NETWORK_ERROR) {
                showToast(getString(R.string.network_error))
            } else if (it == LoadingState.NOT_EXIST) {
                Toast.makeText(applicationContext, getString(R.string.image_not_exist), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun initView() {
    }


    fun finishActivity(view: View) {
        finish()
    }

    fun saveBitmapImage(view: View) {
        if (detailViewModel.state.value != LoadingState.LOADING) {
            val permissionCheck =
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        100
                )
            } else {
                detailViewModel.saveImage()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults[0] == 0) {
                detailViewModel.saveImage()
            } else {
                showToast(getString(R.string.image_save_permission_deny))
            }
        }
    }


}





