package com.hash.cotinum.view.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hash.cotinum.R
import com.hash.cotinum.adapters.ChannelAdapter
import com.hash.cotinum.adapters.ChannelItemsAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_all_channels.*
import kotlinx.android.synthetic.main.activity_home.image_background
import kotlinx.android.synthetic.main.activity_home.text_time
import java.text.SimpleDateFormat
import java.util.*

class AllChannelsActivity : AppCompatActivity(), ChannelAdapter.OnChannelCLickListener {

    private lateinit var channelItemsAdapter: ChannelItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_channels)

        val someHandler = Handler(mainLooper)
        someHandler.postDelayed(object : Runnable {
            override fun run() {
                text_time.text = SimpleDateFormat("EEE HH:mm", Locale.US).format(Date())
                someHandler.postDelayed(this, 1000)
            }
        }, 10)

        Picasso.get().load("http://wallpaperplay.com/walls/full/e/2/b/161070.jpg").into(image_background)

        setUpChannelRecyclerView()
        setUpChannelItemRecyclerView()
    }

    private fun setUpChannelRecyclerView() {
        var linearLayoutManager = LinearLayoutManager(this@AllChannelsActivity)
        recycler_channels.layoutManager = linearLayoutManager
    }

    private fun setUpChannelItemRecyclerView() {
        var linearLayoutManager = LinearLayoutManager(this@AllChannelsActivity)
        recycler_programs.layoutManager = linearLayoutManager

        channelItemsAdapter = ChannelItemsAdapter(this@AllChannelsActivity)
        recycler_programs.adapter = channelItemsAdapter
    }

    override fun onChannelClicked(position: Int) {
        TODO("Not yet implemented")
    }
}