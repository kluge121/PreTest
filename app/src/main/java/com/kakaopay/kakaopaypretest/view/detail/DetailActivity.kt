package com.kakaopay.kakaopaypretest.view.detail

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.GestureDetector
import android.view.MotionEvent
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
import kotlin.math.abs


class DetailActivity : BaseActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(DetailViewModel::class.java)
    }
    lateinit var gestureDetector: GestureDetector

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
        gestureDetector = GestureDetector(this, SwipeDetector(this))
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
                showPermissionDialog()
            }
        }
    }


    private fun showPermissionDialog() {
        AlertDialog.Builder(this).apply {
            title = getString(R.string.dialog_title_permission_request)
            setMessage(getString(R.string.need_permission_msg))
            setPositiveButton(
                getString(R.string.go_to_permission_set)
            ) { dialogInterface, diaglogInt ->

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:$packageName"))
                startActivity(intent)
            }
            setNegativeButton(getString(R.string.msg_cancel)) { dialogInterface, diaglogInt ->
                showToast(getString(R.string.image_save_permission_deny))
            }
        }.create().show()

    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (gestureDetector.onTouchEvent(ev))
            return true
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

}

private class SwipeDetector(val context: Context) : GestureDetector.SimpleOnGestureListener() {

    private val SWIPE_MIN_DISTANCE = 700
    private val X_AXIS_MAX_ALLOW_DISTANCE = 250
    private val SWIPE_THRESHOLD_VELOCITY = 300

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {

        if (abs(e1.x - e2.x) > X_AXIS_MAX_ALLOW_DISTANCE)
            return false

        if (e2.y - e1.y > SWIPE_MIN_DISTANCE && abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            (context as DetailActivity).run {
                finish()
                overridePendingTransition(R.anim.enter_activity, R.anim.finish_activity)
            }
            return true
        }
        return false
    }
}





