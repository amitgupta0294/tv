package com.hash.cotinum.view.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.hash.cotinum.BuildConfig
import com.hash.cotinum.R
import com.hash.cotinum.view.fragments.LoginFragment
import com.hash.cotinum.view.fragments.RegistrationFragment
import kotlinx.android.synthetic.main.activity_splash.text_time
import kotlinx.android.synthetic.main.activity_splash.text_verison
import java.text.SimpleDateFormat
import java.util.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val someHandler = Handler(mainLooper)

        text_verison.text = "Version ${BuildConfig.VERSION_NAME}"

        someHandler.postDelayed(object : Runnable {
            override fun run() {
                text_time.text = SimpleDateFormat("EEE HH:mm", Locale.US).format(Date())
                someHandler.postDelayed(this, 1000)
            }
        }, 10)

        val loginFragment = LoginFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.layout_fragment_container, loginFragment, "id").commit()

    }
}

