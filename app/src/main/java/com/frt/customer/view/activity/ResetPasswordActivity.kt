package com.frt.customer.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.frt.customer.R
import com.frt.customer.util.GlobalMethods
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        initview()
    }

    private fun initview() {
        GlobalMethods.setstaubarColor(this,R.color.white)
        imgBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.imgBack ->{
                onBackPressed()
            }
        }
    }
}