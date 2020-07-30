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

class CastsAdapter(private val context: Context) : RecyclerView.Adapter<CastsAdapter.CastsHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastsHolder {
        var view = layoutInflater.inflate(R.layout.casts_item, parent, false)
        return CastsHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: CastsHolder, position: Int) {

    }

    inner class CastsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    interface ClickListener {
        fun OnItemClick(position: Int)
    }
}