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

class MyDevicesAdapter(private val context: Context) : RecyclerView.Adapter<MyDevicesAdapter.DevicesHolder>() {

    var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesHolder {
        var view = layoutInflater.inflate(R.layout.layout_my_devices, parent, false)
        return DevicesHolder(view)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: DevicesHolder, position: Int) {

    }

    inner class DevicesHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }
}