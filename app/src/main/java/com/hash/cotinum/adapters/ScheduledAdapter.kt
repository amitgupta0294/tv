package com.hash.cotinum.adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import com.hash.cotinum.R
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.view.activities.HomeActivity
import com.hash.cotinum.view.fragments.ScheduledItemDetailsFragment

class ScheduledAdapter(private val context: Context, private var onBtnClick: onButtonClick) : RecyclerView.Adapter<ScheduledAdapter.ShowsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var selectedPosition = -1
    private var onClick : onButtonClick = onBtnClick

    var width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75.0f, context.resources.displayMetrics)
    var height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100.0f, context.resources.displayMetrics)

    var defaultWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35.0f, context.resources.displayMetrics)
    var defaultHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50.0f, context.resources.displayMetrics)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        var view = layoutInflater.inflate(R.layout.layout_scheduled_movies, parent, false)
        return ShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {
        holder.root.setOnClickListener {

            if (selectedPosition != position) {
                holder.root.setBackgroundColor(context.resources.getColor(R.color.colorBlue))
                holder.group.visibility = View.VISIBLE

                holder.imageMovie.layoutParams.height = height.toInt()
                holder.imageMovie.layoutParams.width = width.toInt()

            } else {
                holder.root.setBackgroundColor(context.resources.getColor(android.R.color.transparent))
                holder.group.visibility = View.GONE
                selectedPosition = -1

                holder.imageMovie.layoutParams.height = defaultHeight.toInt()
                holder.imageMovie.layoutParams.width = defaultWidth.toInt()
            }

            selectedPosition = position
        }

        holder.linearRecording.setOnClickListener {
            onClick.onTapped(position)
        }
    }

    inner class ShowsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var root = itemView.findViewById<ConstraintLayout>(R.id.constraint_root)
        var group = itemView.findViewById<Group>(R.id.group_details)
        var imageMovie = itemView.findViewById<ImageView>(R.id.image_movie)

        var linearRecording = itemView.findViewById<LinearLayout>(R.id.linear_recording)
    }

    interface onButtonClick {
        fun onTapped(position : Int)
    }
}