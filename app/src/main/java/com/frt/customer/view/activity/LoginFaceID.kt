package com.frt.customer.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.frt.customer.R
import com.frt.customer.util.GlobalMethods
import kotlinx.android.synthetic.main.activity_login_face_id.*

class LoginFaceID : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_face_id)
        initview()
    }

    private fun initview() {
        GlobalMethods.setstaubarColor(this,R.color.colorF2F2F2)
        tvUseFaceID.setOnClickListener(this)
        imgBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.tvUseFaceID ->{
                startActivity(Intent(this, HomeActivity::class.java))
            }
            R.id.imgBack ->{
                onBackPressed()
            }
        }
    }
}