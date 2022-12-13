package com.frt.customer.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.frt.customer.R
import com.frt.customer.util.GlobalMethods
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initview()
        GlobalMethods.setstaubarColor(this,R.color.white)
    }

    private fun initview() {
        tvBtnSignIn.setOnClickListener(this)
        forgotPasswordLBL.setOnClickListener(this)
        tvSignUp.setOnClickListener(this)
        tvLoginWithFaceId.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.tvBtnSignIn ->{
                startActivity(Intent(this,VerifyPhoneActivity::class.java))
            }
            R.id.forgotPasswordLBL ->{
                startActivity(Intent(this,ResetPasswordActivity::class.java))
            }
            R.id.tvSignUp ->{
                startActivity(Intent(this,SignUpActivity::class.java))
            }
            R.id.tvLoginWithFaceId ->{
                startActivity(Intent(this,LoginFaceID::class.java))
            }
        }
    }


}