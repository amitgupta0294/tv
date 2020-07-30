package com.hash.cotinum.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.view.activities.AllChannelsActivity
import com.hash.cotinum.view.fragments.ProgramsFragment

class OptionsAdapter(private val context: Context, private val programsFragment: ProgramsFragment) : RecyclerView.Adapter<OptionsAdapter.OptionsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsHolder {
        var view = layoutInflater.inflate(R.layout.layout_guide_options, parent, false)
        return OptionsHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: OptionsHolder, position: Int) {
        holder.textOptions.text = "Option $position"

        if (position == 0) {
            holder.textOptions.text = context.getString(R.string.string_favourites)
        } else if (position == 1) {
            holder.textOptions.text = context.getString(R.string.string_all_channels)
        }

        holder.linearRoot.setOnClickListener {

            if (holder.textOptions.text == context.getString(R.string.string_favourites)) {
                programsFragment.setUpFavChannelRecyclerView()
                programsFragment.setUpFavProgramRecyclerView()
            } else if (holder.textOptions.text == context.getString(R.string.string_all_channels)) {
              //  programsFragment.setUpProgramRecyclerView()
            } else {
                context.startActivity(Intent(context, AllChannelsActivity::class.java))
            }
        }
    }

    inner class OptionsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var textOptions = itemView.findViewById<TextView>(R.id.text_option_name)
        var linearRoot = itemView.findViewById<LinearLayout>(R.id.linear_root)
    }

    interface OnChannelCLickListener {
        fun onChannelClicked(position: Int, height : Int)
    }
}