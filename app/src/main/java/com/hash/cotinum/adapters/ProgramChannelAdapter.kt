package com.hash.cotinum.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.model.AppearanceModel
import com.hash.cotinum.view.activities.HistoryActivity

class ProgramChannelAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var view : View

        if (viewType == 1) {
             view = layoutInflater.inflate(R.layout.layout_channel_pre_items, parent, false)
            return OptionHolder(view)
        } else {
             view = layoutInflater.inflate(R.layout.layout_channels_with_info, parent, false)
            return ChannelHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 || position == 1 || position == 2) {
            return 1
        } else {
            return 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is OptionHolder) {

            if (position == 0) {
                holder.imageItem.setImageDrawable(context.resources.getDrawable(R.drawable.ic_list))
                holder.textItem.text = "TV Guide"
            } else if (position == 1) {
                holder.imageItem.setImageDrawable(context.resources.getDrawable(R.drawable.ic_refresh))
                holder.textItem.text = "History"
            } else if (position == 2) {
                holder.imageItem.setImageDrawable(context.resources.getDrawable(R.drawable.ic_date))
                holder.textItem.text = "Reminder"
            }

            holder.imageItem.setOnClickListener {

                var intent = Intent(context, HistoryActivity::class.java)

                if (position == 1) {
                    intent.putExtra("type", "History")
                } else if (position == 2) {
                    intent.putExtra("type", "Reminders")
                } else {
                    intent.putExtra("type", "History")
                }

                context.startActivity(intent)
            }

        } else { }
    }

    inner class ChannelHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imageChannel = itemView.findViewById<ImageView>(R.id.image_show)
    }

    inner class OptionHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imageItem = itemView.findViewById<ImageView>(R.id.image_item)
        var textItem = itemView.findViewById<TextView>(R.id.text_item)
    }

    interface OnChannelCLickListener {
        fun onChannelClicked(position: Int, height : Int)
    }
}