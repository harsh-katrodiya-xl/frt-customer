package com.frt.customer.view.adapter.Home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frt.customer.R
import com.frt.customer.view.Model.exploreModel
import kotlinx.android.synthetic.main.item_explore.view.*
import kotlinx.android.synthetic.main.item_offers.view.*

class OffersAdapter(
    val arrayList: MutableList<exploreModel>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mContext: Context? = null
    var onItemClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_offers, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(mContext!!).load(arrayList[position].image).into(holder.itemView.imdCoupne)
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun updateList(list: ArrayList<exploreModel>) {
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}