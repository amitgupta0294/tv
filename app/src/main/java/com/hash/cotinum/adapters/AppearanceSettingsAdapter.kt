package com.hash.cotinum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.model.AppearanceModel

class AppearanceSettingsAdapter(private val context: Context, private val list : ArrayList<AppearanceModel>, private val onAppearanceClick: onAppearanceItemClick) : RecyclerView.Adapter<AppearanceSettingsAdapter.ShowsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    val onItemClick = onAppearanceClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        var view = layoutInflater.inflate(R.layout.layout_general_settings, parent, false)
        return ShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {

       holder.text.text = list[position].settingName


       if  (!list[position].settingSubName.isBlank()) {
           holder.textCount.text = list[position].settingSubName
           holder.textCount.visibility = View.VISIBLE
       }

        holder.linearRoot.setOnClickListener {
            onItemClick.onAppearanceItemTapped(position)
        }
    }

    inner class ShowsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var text = itemView.findViewById<TextView>(R.id.text_settings)
        var switch = itemView.findViewById<SwitchCompat>(R.id.image_switch)
        var textCount = itemView.findViewById<TextView>(R.id.text_count)
        var linearRoot = itemView.findViewById<LinearLayout>(R.id.linear_root)
    }

    interface onAppearanceItemClick {
        fun onAppearanceItemTapped(position: Int)
    }
}