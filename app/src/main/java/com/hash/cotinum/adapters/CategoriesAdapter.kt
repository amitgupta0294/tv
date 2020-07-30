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

class CategoriesAdapter(private val context: Context, private val list : ArrayList<String>, private val onClickListener: ClickListener) : RecyclerView.Adapter<CategoriesAdapter.ShowsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var selectedIndex = -1
    private var clickListener : ClickListener = onClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        var view = layoutInflater.inflate(R.layout.custom_categories, parent, false)
        return ShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {

       holder.text.text = list[position]

        if (position == 0) {
            holder.text.setTextColor(context.resources.getColor(R.color.colorBlue))
            holder.text.textSize = 22.0f
        }

       holder.linearLayout.setOnClickListener {
           selectedIndex = position
           clickListener.OnItemClick(position)
           notifyDataSetChanged()
       }

        if (selectedIndex == position) {
            holder.text.setTextColor(context.resources.getColor(R.color.colorBlue))
            holder.text.textSize = 22.0f
        } else {
            holder.text.setTextColor(context.resources.getColor(R.color.colorWhite))
            holder.text.textSize = 18.0f
        }
    }

    inner class ShowsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var text = itemView.findViewById<TextView>(R.id.text_category)
        var linearLayout = itemView.findViewById<LinearLayout>(R.id.linear_root)
    }

    interface ClickListener {
        fun OnItemClick(position: Int)
    }
}