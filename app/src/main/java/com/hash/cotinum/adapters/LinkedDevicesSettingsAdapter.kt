package com.hash.cotinum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.model.SettingsModel

class LinkedDevicesSettingsAdapter(private val context: Context, private val list : ArrayList<SettingsModel>) : RecyclerView.Adapter<LinkedDevicesSettingsAdapter.ShowsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        var view = layoutInflater.inflate(R.layout.layout_linked_devices_settings, parent, false)
        return ShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {

       holder.text.text = list[position].settingName
       holder.textCount.text = list[position].settingSubName
    }

    inner class ShowsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var text = itemView.findViewById<TextView>(R.id.text_settings)
        var textCount = itemView.findViewById<TextView>(R.id.text_count)
    }

    interface ClickListener {
        fun OnItemClick(position: Int)
    }
}