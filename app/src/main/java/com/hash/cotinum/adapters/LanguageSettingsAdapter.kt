package com.hash.cotinum.adapters

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.model.AppearanceModel

class LanguageSettingsAdapter(private val context: Context, private val list : ArrayList<String>) : RecyclerView.Adapter<LanguageSettingsAdapter.ShowsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        var view = layoutInflater.inflate(R.layout.layout_language, parent, false)
        return ShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {
        holder.text.text = list[position]

        if (position == selectedPosition) {
            holder.text.isChecked = true
        }

        holder.text.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                selectedPosition = position
                notifyDataSetChanged()
            }
        })
    }

    inner class ShowsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var text = itemView.findViewById<RadioButton>(R.id.radio_button)
    }

    interface ClickListener {
        fun OnItemClick(position: Int)
    }
}