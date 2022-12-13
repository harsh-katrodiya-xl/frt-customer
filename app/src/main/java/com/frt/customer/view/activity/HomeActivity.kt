package com.frt.customer.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.frt.customer.R
import com.frt.customer.util.GlobalMethods
import com.frt.customer.view.Model.exploreModel
import com.frt.customer.view.adapter.Home.ExploreAdapter
import com.frt.customer.view.adapter.Home.OffersAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.math.exp

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    var exporeList = ArrayList<exploreModel>()
    var offersList = ArrayList<exploreModel>()
    val exploreAdapter = ExploreAdapter(mutableListOf())
    val offerceAdapter = OffersAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initview()
    }

    private fun initview() {
        imgLeftIcon.setOnClickListener(this)
        imgRightIcon.setOnClickListener(this)
        GlobalMethods.setstaubarColor(this,R.color.colorF2F2F2)
        setRecyclerview()
    }

    private fun setRecyclerview() {
        exporeList.clear()
        exporeList.add(exploreModel(R.drawable.ic_food, "Food"))
        exporeList.add(exploreModel(R.drawable.ic_courier, "Courier"))
        exporeList.add(exploreModel(R.drawable.ic_grocery, "Grocery"))
        exporeList.add(exploreModel(R.drawable.ic_taxi, "Taxi"))
        exporeList.add(exploreModel(R.drawable.ic_moto, "Moto"))
        exporeList.add(exploreModel(R.drawable.ic_more, "More"))
        exploreAdapter.updateList(exporeList)
        exploreAdapter.onItemClick = { position ->

        }
        rvExplore.setHasFixedSize(true)
        rvExplore.adapter = exploreAdapter

        offersList.clear()
        offersList.add(exploreModel(R.drawable.ic_coupon))
        offersList.add(exploreModel(R.drawable.ic_coupon))
        offersList.add(exploreModel(R.drawable.ic_coupon))
        offerceAdapter.updateList(offersList)
        rvOffers.setHasFixedSize(true)
        rvOffers.adapter = offerceAdapter


    }

    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.imgLeftIcon ->{
                drawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.imgRightIcon ->{
                startActivity(Intent(this,LocationActivity::class.java))
            }
        }
    }

    fun clickNavigation(view: View) {
        drawerLayout.closeDrawers()
        when (view.id) {
            R.id.navHome -> {
                Toast.makeText(this,"Home",Toast.LENGTH_SHORT)
            }
            R.id.navmyorder -> {

            }
            R.id.navPayment -> {

            }
            R.id.navNotification -> {

            }
            R.id.navhelp -> {

            }
            R.id.navsetting -> {

            }
            R.id.lnProfile -> {

            }

        }
    }
}