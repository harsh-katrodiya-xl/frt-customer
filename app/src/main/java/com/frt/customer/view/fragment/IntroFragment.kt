package com.frt.customer.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.bsk.parentt.util.makeGone
import com.bsk.parentt.util.makeVisible
import com.frt.customer.R

import com.frt.customer.base.BaseFragment
import com.frt.customer.util.GlobalMethods
import com.frt.customer.view.activity.SignInActivity
import com.frt.customer.view.adapter.Intro.IntroViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_intro.*


class IntroFragment : BaseFragment(), View.OnClickListener {

    var introViewPagerAdapter: IntroViewPagerAdapter? = null
    var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalMethods.setstaubarColor(requireActivity(),R.color.white)
        initView()
    }

    private fun initView() {
        tvSignIn.setOnClickListener(this)
        imgNext.setOnClickListener(this)
        tvGetStarted.setOnClickListener(this)
        var titles = arrayOf("One app for all services", "Book professionals", "Instant or schedule services", "Real Time Tracking")
        var descs = arrayOf(
            "Get a food, flower, grocery, gardener, cleaner, parcel, plumber and much more, at your finger tips",
            "Book Professionals or order from local favorites near you.\nWhatever you want, we get it as easy and fast as 1-2-3!    ",
            "We’ve got everything you need available within an hour or schedule at your prefer time.",
            "Track your delivery & services via real time navigation"
        )
        introViewPagerAdapter =
            IntroViewPagerAdapter(
                requireContext(),
                titles,
                descs
            )
        viewpager.adapter = introViewPagerAdapter
        tab_indicator.setViewPager(viewpager)
        viewpager.clipChildren = false
        viewpager.offscreenPageLimit = 4
        viewpager.currentItem = 0
        viewpager.isClickable = false
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                currentPosition = position
                if (position == 3) {
                    imgNext.makeGone()
                    tvGetStarted.makeVisible()
                    tvSignIn.makeGone()
                } else {
                    imgNext.makeVisible()
                    tvGetStarted.makeGone()
                    tvSignIn.makeVisible()
                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tvSignIn -> {
                requireActivity().finish()
                startActivity(Intent(requireContext(), SignInActivity::class.java))
            }
            R.id.imgNext -> {
                viewpager.currentItem = currentPosition + 1
                if (currentPosition == 2) {
                    imgNext.makeGone()
                    tvGetStarted.makeVisible()
                    tvSignIn.makeGone()
                } else {
                    imgNext.makeVisible()
                    tvGetStarted.makeGone()
                    tvSignIn.makeVisible()
                }
            }
            R.id.tvGetStarted ->{
                requireActivity().finish()
                startActivity(Intent(requireContext(), SignInActivity::class.java))
            }
        }
    }
}