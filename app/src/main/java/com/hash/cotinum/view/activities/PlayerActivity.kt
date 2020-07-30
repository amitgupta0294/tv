package com.hash.cotinum.view.activities

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.hash.cotinum.R
import com.hash.cotinum.adapters.ProgramChannelAdapter
import com.hash.cotinum.m3uparser.M3UItem
import com.hash.cotinum.view.OnFragmentBackClicked
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.activity_player.drawer_layout
import okhttp3.internal.userAgent
import render.animations.Render

class PlayerActivity : AppCompatActivity(), Player.EventListener {

    private lateinit var programChannelAdapter: ProgramChannelAdapter
    private var menuVisibility : Int = 0
    var slideUpRender = Render(this@PlayerActivity)
    var slideUpRenderChannels = Render(this@PlayerActivity)

    var slideDownRender = Render(this@PlayerActivity)
    var fadeRender = Render(this@PlayerActivity)

    lateinit var channelData : M3UItem
    private var url : String = ""

    private lateinit var simpleExoplayer: SimpleExoPlayer
    private var playbackPosition = 0L
    private lateinit var imageSetting : ImageView

    private val bandwidthMeter by lazy {
        DefaultBandwidthMeter()
    }
    private val adaptiveTrackSelectionFactory by lazy {
        AdaptiveTrackSelection.Factory(bandwidthMeter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        imageSetting = channel_player.findViewById(R.id.image_setting)

        drawer_layout.closeDrawer(GravityCompat.END)
        imageSetting.setOnClickListener {

            drawer_layout.openDrawer(GravityCompat.END)

            /*if (constraint_settings.visibility == View.VISIBLE) {
                constraint_settings.visibility = View.GONE
            } else {
                constraint_settings.visibility = View.VISIBLE
            }*/
        }

        fadeRender.setDuration(500)
        slideUpRender.setDuration(500)
        slideDownRender.setDuration(500)
        slideUpRenderChannels.setDuration(500)

        getIntentData()
        setChannels()
    }

    private fun getIntentData() {
        channelData = intent?.getSerializableExtra("channel_data") as M3UItem
        if (channelData.itemUrl != null) url = channelData.itemUrl!!
        initializeExoplayer()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {

      /*  if (menuVisibility == 0) {
            menuVisibility = 1
            constraint_options.visibility = View.VISIBLE

            slideUpRender.setAnimation(Slide().InUp(constraint_options))
            slideUpRender.start()

            fadeRender.setAnimation(Slide().OutUp(group_controls))
            fadeRender.start()

            group_controls.visibility = View.GONE
        } else  if (menuVisibility == 1) {
            menuVisibility = 2
            constraint_options.visibility = View.VISIBLE
            group_channels.visibility = View.VISIBLE

            slideUpRenderChannels.setAnimation(Slide().InUp(group_channels))
            slideUpRender.start()
        }
*/
        return super.onKeyUp(keyCode, event)
    }

    override fun onBackPressed() {

        if (drawer_layout.isDrawerOpen(navigation_view)) {

            drawer_layout.closeDrawer(GravityCompat.END)
            /*val fragment = this.supportFragmentManager.findFragmentById(R.id.linear_settings_fragment_container)

            (fragment as? OnFragmentBackClicked)?.onBackTapped().let {
                // super.onBackPressed()


            }*/
        } else {
            releaseExoplayer()
            super.onBackPressed()
        }

    }

    private fun setChannels() {
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        recycler_channels.layoutManager = linearLayoutManager

        programChannelAdapter = ProgramChannelAdapter(this)
        recycler_channels.adapter = programChannelAdapter
    }

    private fun initializeExoplayer() {
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(this,
            DefaultRenderersFactory(this),
            DefaultTrackSelector(adaptiveTrackSelectionFactory),
            DefaultLoadControl()
        )

        prepareExoplayer()
        channel_player.player = simpleExoplayer
        simpleExoplayer.seekTo(playbackPosition)
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(this)
    }

    private fun releaseExoplayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
        simpleExoplayer.stop()
        simpleExoplayer.clearVideoSurface()
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
       return HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri)
    }

    private fun prepareExoplayer() {

        if (!url.isNullOrEmpty()) {
            val uri = Uri.parse(url)
            val mediaSource = buildMediaSource(uri)
            simpleExoplayer.prepare(mediaSource)
        }
    }
}