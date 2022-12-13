package com.frt.customer.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.frt.customer.view.fragment.IntroFragment
import com.frt.customer.R
import com.frt.customer.base.BaseActivity

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        replaceFragment(IntroFragment(),false)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, IntroActivity::class.java)
        }
    }
}