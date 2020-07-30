package com.hash.cotinum.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.hash.cotinum.R
import com.hash.cotinum.adapters.ChannelHistoryAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_history.text_time
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var type : String
    private lateinit var channelHistoryAdapter: ChannelHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        getIntentData()
        setUpRecyclerview()

        //TODO : to be removed after implementation
        Picasso.get().load("http://wallpaperplay.com/walls/full/e/2/b/161070.jpg").into(image_background)

        val someHandler = Handler(mainLooper)
        someHandler.postDelayed(object : Runnable {
            override fun run() {
                text_time.text = SimpleDateFormat("EEE HH:mm", Locale.US).format(Date())
                someHandler.postDelayed(this, 1000)
            }
        }, 10)
    }

    fun getIntentData() {

        type = intent.getStringExtra("type")!!

        if (!type.isNullOrEmpty()) {
            if (type == "History") {
                text_page_title.text = "History"
            } else if (type == "Reminders") {
                text_page_title.text = "Reminders"
            }
        } else {
            text_page_title.text = "History"
        }
    }

    private fun setUpRecyclerview() {
        var linearLayoutManager = LinearLayoutManager(this)
        recycler_iems.layoutManager = linearLayoutManager

        channelHistoryAdapter = ChannelHistoryAdapter(this@HistoryActivity)
        recycler_iems.adapter = channelHistoryAdapter
    }
}