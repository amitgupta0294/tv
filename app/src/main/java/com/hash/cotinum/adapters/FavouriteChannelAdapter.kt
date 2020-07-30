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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.model.AppearanceModel

class FavouriteChannelAdapter(private val context: Context) : RecyclerView.Adapter<FavouriteChannelAdapter.ChannelHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        var view = layoutInflater.inflate(R.layout.layout_channels_item, parent, false)
        return ChannelHolder(view)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: ChannelHolder, position: Int) {
        holder.imageFavourite.setImageDrawable(context.resources.getDrawable(R.drawable.ic_marked_fav))
    }

    inner class ChannelHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imageChannel = itemView.findViewById<ImageView>(R.id.image_channel_pic)
        var linearLayout = itemView.findViewById<LinearLayout>(R.id.linear_layout)
        var linearRoot = itemView.findViewById<ConstraintLayout>(R.id.linear_root)
        var image = itemView.findViewById<ImageView>(R.id.image)
        var imageFavourite = itemView.findViewById<ImageView>(R.id.image_favourite)
        var selectedView = itemView.findViewById<View>(R.id.view_selected)
    }

    interface OnChannelCLickListener {
        fun onChannelClicked(position: Int, height : Int)
    }
}