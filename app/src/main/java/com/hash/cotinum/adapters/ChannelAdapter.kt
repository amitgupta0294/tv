package com.hash.cotinum.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.m3uparser.M3UItem
import com.hash.cotinum.model.AppearanceModel
import com.hash.cotinum.view.activities.PlayerActivity
import com.squareup.picasso.Picasso

class ChannelAdapter(private val context: Context, private val onChannelCLickListener: OnChannelCLickListener, private val channelList : ArrayList<M3UItem>) : RecyclerView.Adapter<ChannelAdapter.ChannelHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    val clickListener = onChannelCLickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        var view = layoutInflater.inflate(R.layout.layout_channels_item, parent, false)
        return ChannelHolder(view)
    }

    override fun getItemCount(): Int {
        return channelList.size
    }

    override fun onBindViewHolder(holder: ChannelHolder, position: Int) {

        holder.textChannelName.text = channelList[position].itemName

        if (channelList[position].itemIcon != null || channelList[position].itemIcon != "") {
            Log.e("icon_url", channelList[position].itemIcon)
            Picasso.get().load(channelList[position].itemIcon ).into(holder.imageChannel)
        } else {
            holder.imageChannel.setImageDrawable(context.resources.getDrawable(R.drawable.channel2))
        }

        holder.linearRoot.setOnClickListener {
            var intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("channel_data", channelList[position])
            context.startActivity(intent)
        }
    }

    inner class ChannelHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imageChannel = itemView.findViewById<ImageView>(R.id.image_channel_pic)
        var linearLayout = itemView.findViewById<LinearLayout>(R.id.linear_layout)
        var linearRoot = itemView.findViewById<ConstraintLayout>(R.id.linear_root)
        var image = itemView.findViewById<ImageView>(R.id.image)
        var selectedView = itemView.findViewById<View>(R.id.view_selected)

        var textChannelName = itemView.findViewById<TextView>(R.id.text_channel_name)
    }

    interface OnChannelCLickListener {
        fun onChannelClicked(position: Int)
    }
}