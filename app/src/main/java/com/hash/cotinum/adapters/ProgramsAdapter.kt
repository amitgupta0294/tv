package com.hash.cotinum.adapters

import android.content.Context
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.m3uparser.M3UItem
import com.hash.cotinum.model.epg.Programme
import com.hash.cotinum.view.activities.HomeActivity
import com.hash.cotinum.view.fragments.RecordFragment

class ProgramsAdapter(private val context: Context, private val channelList : ArrayList<M3UItem>, private var programmeMap : Map<String, ArrayList<Programme>>?) : RecyclerView.Adapter<ProgramsAdapter.ChannelHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        var view = layoutInflater.inflate(R.layout.layout_linear, parent, false)
        return ChannelHolder(view)
    }

    override fun getItemCount(): Int {
        return channelList.size
    }

    override fun onBindViewHolder(holder: ChannelHolder, position: Int) {

       var list = programmeMap?.get(channelList[position].itemName)

        repeat(list!!.size) {

            Log.e("list_size", list.size.toString())

            val diffMinutes : Long = (list[it].stopDate?.replaceAfter(" ", "")?.trim()?.toLong()?.minus(list[it].startDate?.replaceAfter(" ", "")?.trim()?.toLong()!!))!!
            Log.e("minutes --->" , diffMinutes.toString())

            var layout = LayoutInflater.from(context).inflate(R.layout.layout_program_item, null)

            var text = layout.findViewById<TextView>(R.id.text_program_name)
            var linearLayout = layout.findViewById<ConstraintLayout>(R.id.linear_layout)
            var constraintRoot = layout.findViewById<ConstraintLayout>(R.id.constraint_root)
            var programProgress = layout.findViewById<ProgressBar>(R.id.program_progress)

            if (position == 1 && it == 1) {
                programProgress.visibility = View.VISIBLE
            }

            text.text = list[it].title

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

                val wrapper: Context = ContextThemeWrapper(context, R.style.PopupMenu)
                val popup = PopupMenu(wrapper, constraintRoot)

                popup.menuInflater.inflate(R.menu.program_popup_menu, popup.menu)

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