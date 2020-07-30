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

class SettingsAdapter(private val context: Context, private val list : ArrayList<String>, private val onClickListener: ClickListener) : RecyclerView.Adapter<SettingsAdapter.ShowsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var clickListener : ClickListener = onClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        var view = layoutInflater.inflate(R.layout.layout_settings_item, parent, false)
        return ShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {
        holder.text.text = list[position]
        holder.linearLayout.setOnClickListener {
            clickListener.OnItemClick(position)
        }
    }

    inner class ShowsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var text = itemView.findViewById<TextView>(R.id.text_settings)
        var linearLayout = itemView.findViewById<LinearLayout>(R.id.linear_root)
    }

    interface ClickListener {
        fun OnItemClick(position: Int)
    }
}