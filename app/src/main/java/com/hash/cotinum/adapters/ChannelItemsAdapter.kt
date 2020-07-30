package com.hash.cotinum.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.view.activities.AllChannelsActivity

class ChannelItemsAdapter(private val context: Context) : RecyclerView.Adapter<ChannelItemsAdapter.OptionsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsHolder {
        var view = layoutInflater.inflate(R.layout.layout_channel_programs, parent, false)
        return OptionsHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: OptionsHolder, position: Int) {

    }

    inner class OptionsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var textOptions = itemView.findViewById<TextView>(R.id.text_item)
    }

    interface OnChannelCLickListener {
        fun onChannelClicked(position: Int, height : Int)
    }
}