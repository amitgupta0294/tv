package com.hash.cotinum.background

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hash.cotinum.m3uparser.M3UItem
import com.hash.cotinum.model.epg.Programme
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class EpgDownloader(ctx : Context, params : WorkerParameters) : Worker(ctx, params) {

    val DATA_URL = "http://tvservice.pro/xmltv.php?username=ours2020&password=61D15ACF9073740"
    var cotinumXmlParser = CotinumXmlParser()

    override fun doWork(): Result {

        return try {

            Log.e("running_workmanager", "======================= >>")
            loadXmlFromNetwork(DATA_URL)

            Result.success()
        } catch (e : Exception) {
            Log.e("running_workmanager", e.toString())
            Result.failure()
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun loadXmlFromNetwork(url : String) : Unit {

        try {
            downloadUrl(url)?.use {

                Log.e("input_stream", it.toString())

                var channelsList = cotinumXmlParser.parseChannels(it)
                Log.e(" channels_list_workmanager", channelsList.size.toString())

                if (channelsList.isNotEmpty()) {
                    var programmesList = cotinumXmlParser.parseProgrammes(it)
                    Log.e(" program_list_workmanager", programmesList.size.toString())
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun downloadUrl(urlString: String): InputStream? {
        val url = URL(urlString)
        return (url.openConnection() as? HttpURLConnection)?.run {
            readTimeout = 300000
            connectTimeout = 300000
            requestMethod = "GET"
            doInput = true
            // Starts the query
            connect()
            inputStream
        }
    }
}