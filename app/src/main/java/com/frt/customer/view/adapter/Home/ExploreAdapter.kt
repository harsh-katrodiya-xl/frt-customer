package com.frt.customer.view.adapter.Home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
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

class ExploreAdapter(
    val arrayList: MutableList<exploreModel>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mContext: Context? = null
    var onItemClick: ((position: Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_explore, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(mContext!!).load(arrayList[position].image).into(holder.itemView.imgexplore)
        holder.itemView.tvTitle.text = "" + arrayList[position].title

        if (arrayList[position].title == "More") {
            holder.itemView.cardviewMain.setCardBackgroundColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.colorCBE8FF
                )
            )
        } else {
            holder.itemView.cardviewMain.setCardBackgroundColor(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.white
                )
            )
            holder.itemView.imgexplore.setColorFilter(
                ContextCompat.getColor(
                    mContext!!,
                    R.color.colorPrimary
                )
            )
        }

        holder.itemView.setOnTouchListener { v, event ->
            if (arrayList[position].title != "More") {
                if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                    holder.itemView.cardviewMain.setCardBackgroundColor(
                        ContextCompat.getColor(
                            mContext!!,
                            R.color.white
                        )
                    )
                    holder.itemView.tvTitle.setTextColor(ContextCompat.getColor(
                        mContext!!,
                        R.color.colorgray
                    ))
                    holder.itemView.imgexplore.setColorFilter(
                        ContextCompat.getColor(
                            mContext!!,
                            R.color.colorPrimary
                        )
                    )
                    onItemClick!!.invoke(position)
                } else {
                    holder.itemView.cardviewMain.setCardBackgroundColor(
                        ContextCompat.getColor(
                            mContext!!,
                            R.color.colorPrimary
                        )
                    )
                    holder.itemView.tvTitle.setTextColor(ContextCompat.getColor(
                        mContext!!,
                        R.color.white
                    ))
                    holder.itemView.imgexplore.setColorFilter(
                        ContextCompat.getColor(
                            mContext!!,
                            R.color.white
                        )
                    )
                }
            }
            true;
        }
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