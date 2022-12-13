package com.frt.customer.view.activity

import android.content.Intent
import android.os.Bundle
import com.frt.customer.R
import com.frt.customer.base.BaseActivity
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        Timer("afterSplash", false)
            .schedule(TimeUnit.SECONDS.toMillis(3)) {
                launchHomeScreen()
            }
    }

    private fun launchHomeScreen() {
        finish()
        startActivity(Intent(this, IntroActivity::class.java))
    }
}