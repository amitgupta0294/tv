package com.hash.cotinum.adapters

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.model.AppearanceModel

class TimeAdapter(private val context: Context) : RecyclerView.Adapter<TimeAdapter.ChannelHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        var view = layoutInflater.inflate(R.layout.layout_item_time, parent, false)
        return ChannelHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ChannelHolder, position: Int) {

    }

    inner class ChannelHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    interface onAppearanceItemClick {
        fun onAppearanceItemTapped(position: Int)
    }
}