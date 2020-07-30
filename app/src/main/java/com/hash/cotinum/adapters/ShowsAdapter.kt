package com.hash.cotinum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R

class ShowsAdapter(private val context: Context, private val list : ArrayList<String>, private val onClicked : onItemClick) : RecyclerView.Adapter<ShowsAdapter.ShowsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val onItemClicked = onClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        var view = layoutInflater.inflate(R.layout.layout_show, parent, false)
        return ShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {

        holder.image.setOnClickListener {
            onClicked.onClick(position)
        }

        when {
            position % 3 == 0 -> {
                holder.image.setImageDrawable(context.resources.getDrawable(R.drawable.img1))
            }
            position % 3 == 1 -> {
                holder.image.setImageDrawable(context.resources.getDrawable(R.drawable.img2))
            }
            position % 3 == 2 -> {
                holder.image.setImageDrawable(context.resources.getDrawable(R.drawable.img3))
            }
        }
    }

    inner class ShowsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.image_show)
    }

    interface onItemClick {
        fun onClick(position : Int)
    }
}