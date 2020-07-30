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
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R

class RemoteControlSettingsAdapter(private val context: Context, private val list : ArrayList<String>) : RecyclerView.Adapter<RemoteControlSettingsAdapter.ShowsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        var view = layoutInflater.inflate(R.layout.layout_general_settings, parent, false)
        return ShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {

       holder.text.text = list[position]
       holder.switch.visibility = View.VISIBLE
    }

    inner class ShowsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var text = itemView.findViewById<TextView>(R.id.text_settings)
        var textCount = itemView.findViewById<TextView>(R.id.text_count)

        var switch = itemView.findViewById<SwitchCompat>(R.id.image_switch)
    }

    interface ClickListener {
        fun OnItemClick(position: Int)
    }
}