package com.hash.cotinum

import android.app.Application
import android.content.Context
import io.paperdb.Paper

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Paper.init(appContext)
    }

    companion object {
        lateinit var appContext: Context
    }
}