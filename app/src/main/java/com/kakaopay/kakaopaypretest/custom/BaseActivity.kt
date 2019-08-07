package com.kakaopay.kakaopaypretest.custom

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    private var toast: Toast? = null
    abstract fun initView()
    abstract fun initBind()

    fun showToast(msg: String) {
        if (toast == null) {
            toast = Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(msg)
        }
        toast?.show()
    }

}
