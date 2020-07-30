package com.hash.cotinum.adapters

import android.R.attr.button
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.view.activities.HomeActivity
import com.hash.cotinum.view.fragments.RecordFragment


class FavouriteProgramsAdapter(private val context: Context, private val openPosition : Int, private val list : ArrayList<Int>) : RecyclerView.Adapter<FavouriteProgramsAdapter.ChannelHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        var view = layoutInflater.inflate(R.layout.layout_linear, parent, false)
        return ChannelHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ChannelHolder, position: Int) {

        if (position == openPosition) {
            holder.linearLayout.layoutParams.height = list[0]
        }

        repeat(2) {
            var layout = LayoutInflater.from(context).inflate(R.layout.layout_program_item, null)

            var text = layout.findViewById<TextView>(R.id.text_program_name)
            var linearLayout = layout.findViewById<ConstraintLayout>(R.id.linear_layout)
            var constraintRoot = layout.findViewById<ConstraintLayout>(R.id.constraint_root)
            var programProgress = layout.findViewById<ProgressBar>(R.id.program_progress)

            if (position == 1 && it == 1) {
                programProgress.visibility = View.VISIBLE
            }

            text.text = "Program"

            if (position == 1 && it == 4) {
                linearLayout.layoutParams.width = 550
            } else if (position == 3 && it == 1) {
                linearLayout.layoutParams.width = 180
            } else if (position == 4 && it == 6) {
                linearLayout.layoutParams.width = 750
            } else if (position == 5 && it == 2) {
                linearLayout.layoutParams.width = 200
            }

            if ((position == 1) && (it == 1)) {
                linearLayout.layoutParams.width = 550
            }

            constraintRoot.setOnClickListener {

                val popup = PopupMenu(context, constraintRoot)

                popup.getMenuInflater().inflate(R.menu.program_popup_menu, popup.menu)

                popup.setOnMenuItemClickListener { item ->
                    if (item?.title?.equals("Record")!!) {
                        val ft = (context as HomeActivity).supportFragmentManager.beginTransaction()
                        ft.setCustomAnimations(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
                        ft.replace(R.id.linear_fragment_container, RecordFragment(), "id").addToBackStack(null).commit()
                    }

                    true
                }

                popup.show() //showing popup menu

            }

            holder.linearLayout.addView(layout)
        }
    }



    inner class ChannelHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var linearLayout = itemView.findViewById<LinearLayout>(R.id.linear_layout)
        var linearRoot = itemView.findViewById<LinearLayout>(R.id.linear_root)
    }

    interface onAppearanceItemClick {
        fun onAppearanceItemTapped(position: Int)
    }
}