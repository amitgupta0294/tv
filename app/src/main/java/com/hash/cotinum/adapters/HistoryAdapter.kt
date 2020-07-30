package com.hash.cotinum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R

class HistoryAdapter(private val context: Context) : RecyclerView.Adapter<HistoryAdapter.ShowsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsHolder {
        var view = layoutInflater.inflate(R.layout.layout_history_item, parent, false)
        return ShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ShowsHolder, position: Int) {


    }

    inner class ShowsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) { }
}