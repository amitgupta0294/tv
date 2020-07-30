package com.hash.cotinum.adapters

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R

class ChannelHistoryAdapter(private val context: Context) : RecyclerView.Adapter<ChannelHistoryAdapter.ShowsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        var view = layoutInflater.inflate(R.layout.layout_history, parent, false)
        return ShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {

    }

    inner class ShowsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var text = itemView.findViewById<TextView>(R.id.text_category)
        var linearLayout = itemView.findViewById<LinearLayout>(R.id.linear_root)
    }

    interface ClickListener {
        fun OnItemClick(position: Int)
    }
}