package com.hash.cotinum.view.activities

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.material.tabs.TabLayout
import com.hash.cotinum.MyApplication
import com.hash.cotinum.R
import com.hash.cotinum.adapters.Pager
import com.hash.cotinum.background.EpgDownloader
import com.hash.cotinum.http.ApiInterface
import com.hash.cotinum.http.RetrofitService
import com.hash.cotinum.model.ChannelItem
import com.hash.cotinum.model.signup.Tv
import com.hash.cotinum.view.OnFragmentBackClicked
import com.hash.cotinum.view.fragments.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory


class HomeActivity : AppCompatActivity() {

    private var tabList = arrayListOf("Guide", "DVR", "On Demand", "Apps")
    private var apiInterface = RetrofitService.createXMLService(ApiInterface::class.java)

    private lateinit var recyclerSettings : RecyclerView

    private lateinit var pager: Pager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

      //  WorkManager.getInstance(MyApplication.appContext).enqueue(OneTimeWorkRequest.from(EpgDownloader::class.java))

       // getEPG()

        pager = Pager(supportFragmentManager, 4)
        view_pager.adapter = pager

        for (j in 0 until tabList.size) {
            tab.addTab(tab.newTab().setText(tabList[j]))
        }

        image_settings.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    image_settings.setColorFilter(ContextCompat.getColor(this@HomeActivity, R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
                } else {
                    image_settings.setColorFilter(ContextCompat.getColor(this@HomeActivity, R.color.colorWhite), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
            }
        })

        image_search.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    image_search.setColorFilter(ContextCompat.getColor(this@HomeActivity, R.color.colorBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
                } else {
                    image_search.setColorFilter(ContextCompat.getColor(this@HomeActivity, R.color.colorWhite), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
            }
        })

        drawer_layout.closeDrawer(GravityCompat.END)

        image_settings.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.END)
        }

        image_search.setOnClickListener {
            loadFragment(SearchFragment())
        }

        val someHandler = Handler(mainLooper)
        someHandler.postDelayed(object : Runnable {
            override fun run() {
                text_time.text = SimpleDateFormat("EEE HH:mm", Locale.US).format(Date())
                someHandler.postDelayed(this, 1000)
            }
        }, 10)

        //TODO : to be removed after implementation
        Picasso.get().load("http://wallpaperplay.com/walls/full/e/2/b/161070.jpg").into(image_background)

        setCustomTabs()
        loadFragment(ProgramsFragment())

        val settingsFragment = SettingsFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.linear_settings_fragment_container, settingsFragment, "id").commit()

        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                var textView = tab?.customView?.findViewById<TextView>(R.id.text_tab_title)
                textView?.setTextColor(resources.getColor(R.color.colorWhite))
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                view_pager.currentItem = tab?.position!!

                var textView = tab.customView?.findViewById<TextView>(R.id.text_tab_title)
                textView?.setTextColor(resources.getColor(R.color.colorBlue))

                if (tab.position == 0) {
                    loadFragment(ProgramsFragment())
                } else if (tab.position == 1) {
                    loadFragment(DVRFragment())
                } else if (tab.position == 2) {
                    loadFragment(OnDemandFragment())
                } else if (tab.position == 3) {
                    loadFragment(AppsFragment())
                }
            }
        })

        tab.getTabAt(0)?.select()
    }

    override fun onBackPressed() {

        if (drawer_layout.isDrawerOpen(navigation_settings)) {
            val fragment = this.supportFragmentManager.findFragmentById(R.id.linear_settings_fragment_container)

            (fragment as? OnFragmentBackClicked)?.onBackTapped().let {
               // super.onBackPressed()


            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        var f = supportFragmentManager.findFragmentById(R.id.linear_fragment_container)

        if (f is ProgramsFragment) {
            f.onDPadKeyDown(keyCode, event)
        }

        return super.onKeyDown(keyCode, event)
    }

    private fun setCustomTabs() {
        for (i in 0 until tab.tabCount) {

            var tabLayout = tab.getTabAt(i)

            var linearLayout = LayoutInflater.from(this@HomeActivity)
                    .inflate(R.layout.custom_textview, tab, false)

            var textView = linearLayout.findViewById<TextView>(R.id.text_tab_title)
            textView.text = tabList[i]

            tabLayout?.customView = linearLayout
        }
    }

   fun getChannelList() : String {
        try {
            val url = URL("http://tvservice.pro/xmltv.php?username=ours2020&password=61D15ACF9073740")
            val dbf: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
            val db: DocumentBuilder = dbf.newDocumentBuilder()
            val doc: Document = db.parse(InputSource(url.openStream()))
            doc.documentElement.normalize()
            val nodeList: NodeList = doc.getElementsByTagName("tv")

            Log.e("node_list", nodeList.length.toString())

            for (i in 0 until nodeList.getLength()) {

            }
        } catch (e: Exception) {
            Log.e("XML Pasing Excpetionsuper.onPostExecute(result) = $e", "")
        }

       return ""
    }

    private fun loadFragment(fragment : Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        ft.replace(R.id.linear_fragment_container, fragment, "id").commit()
    }

    fun getEPG() {

        var channelData = MutableLiveData<ArrayList<ChannelItem>>()

        apiInterface.getXml().enqueue(object : Callback<Tv>{
            override fun onFailure(call: Call<Tv>, t: Throwable) {
                Log.e("channel_error", t.toString())
            }

            override fun onResponse(call: Call<Tv>, response: Response<Tv>) {
                Log.e("channel_data", response.isSuccessful.toString())
            }
        })
    }
}