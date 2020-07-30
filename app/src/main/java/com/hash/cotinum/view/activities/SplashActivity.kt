package com.hash.cotinum.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.hash.cotinum.BuildConfig
import com.hash.cotinum.R
import kotlinx.android.synthetic.main.activity_splash.*
import java.text.SimpleDateFormat
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val someHandler = Handler(mainLooper)

        text_verison.text = "Version "+ BuildConfig.VERSION_NAME

        someHandler.postDelayed(object : Runnable {
            override fun run() {
                text_time.text = SimpleDateFormat("EEE HH:mm", Locale.US).format(Date())
                someHandler.postDelayed(this, 1000)
            }
        }, 10)

        Handler().postDelayed({

            var intent = Intent(this@SplashActivity, LoginActivity::class.java)
            this@SplashActivity.startActivity(intent)
            overridePendingTransition(
                R.anim.anim_next_slide_in,
                R.anim.anim_next_slide_out
            )
            this@SplashActivity.finish()

        }, 2500)
    }
}