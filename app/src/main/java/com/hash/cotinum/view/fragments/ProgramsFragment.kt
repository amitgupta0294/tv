package com.hash.cotinum.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.hash.cotinum.R
import com.hash.cotinum.adapters.*
import com.hash.cotinum.m3uparser.M3UItem
import com.hash.cotinum.m3uparser.M3UParser
import com.hash.cotinum.model.epg.Programme
import kotlinx.android.synthetic.main.exo_playback_control_view.*
import kotlinx.android.synthetic.main.fragment_programs.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException

class ProgramsFragment : Fragment(), ChannelAdapter.OnChannelCLickListener {

    private lateinit var channelAdapter: ChannelAdapter
    private lateinit var favouriteChannelAdapter: FavouriteChannelAdapter

    private lateinit var timeAdapter: TimeAdapter
    private lateinit var programsAdapter: ProgramsAdapter
    private lateinit var favouriteProgramsAdapter: FavouriteProgramsAdapter
    private lateinit var optionsAdapter: OptionsAdapter

    private lateinit var scrollListeners1: RecyclerView.OnScrollListener
    private lateinit var scrollListeners2: RecyclerView.OnScrollListener

    private lateinit var arrayList: ArrayList<Int>
    private var openPosition = -1

    private lateinit var channelList: ArrayList<M3UItem>
    private lateinit var programmeList: ArrayList<Programme>

    var display_name: String? = null
    var icon: String? = null
    var url: String? = null

    var title: String? = null
    var sub_title: String? = null
    var name : String? = null
    var desc: String? = null
    var category: String? = null
    var stopDate: String? = null
    var startDate: String? = null


    private var epgMap : MutableMap<String, ArrayList<Programme>> ?= null
    var programList = listOf<Programme>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_programs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        channelList = ArrayList()
        programmeList = ArrayList()
        epgMap = HashMap()

        setUpTimeRecyclerView()
        setUpOptionsRecyclerView()

        parseXMLFile()

        scrollListeners1 = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                recycler_time.removeOnScrollListener(scrollListeners1)
                recycler_time.scrollBy(dx, dy)
                recycler_time.addOnScrollListener(scrollListeners1)
            }
        }

        scrollListeners2 = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        }


        recycler_programs.addOnScrollListener(scrollListeners1)
        recycler_channels.addOnScrollListener(scrollListeners2)

        // readM3UFile()
    }

    fun onDPadKeyDown(key: Int, keyEvent: KeyEvent?) {

        if (recycler_channels.hasFocus() && key == KeyEvent.KEYCODE_DPAD_LEFT) {
            group_options.visibility = View.VISIBLE
        }
    }

    fun setUpChannelRecyclerView(list: ArrayList<M3UItem>) {
        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recycler_channels.layoutManager = linearLayoutManager

        channelAdapter = ChannelAdapter(this.requireActivity(), this, list)
        recycler_channels.adapter = channelAdapter

        setUpProgramRecyclerView(list.size)
    }

    fun setUpFavChannelRecyclerView() {
        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recycler_channels.layoutManager = linearLayoutManager

        favouriteChannelAdapter = FavouriteChannelAdapter(this.requireActivity())
        recycler_channels.adapter = favouriteChannelAdapter
    }

    private fun setUpOptionsRecyclerView() {
        var linearLayoutManager = GridLayoutManager(this.requireActivity(), 1)
        recycler_options.layoutManager = linearLayoutManager

        optionsAdapter = OptionsAdapter(this.requireActivity(), this)
        recycler_options.adapter = optionsAdapter
    }


    private fun setUpTimeRecyclerView() {
        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        recycler_time.layoutManager = linearLayoutManager

        timeAdapter = TimeAdapter(this.requireActivity())
        recycler_time.adapter = timeAdapter
    }

    fun setUpProgramRecyclerView(size: Int) {

        arrayList = ArrayList()
        repeat(8) {
            arrayList.add(0)
        }

        var staggeredGridLayoutManager =
            StaggeredGridLayoutManager(size, GridLayoutManager.HORIZONTAL)
        recycler_programs.layoutManager = staggeredGridLayoutManager

        programsAdapter = ProgramsAdapter(this.requireActivity(), channelList, epgMap)
        recycler_programs.adapter = programsAdapter
    }

    fun setUpFavProgramRecyclerView() {

        arrayList = ArrayList()
        repeat(2) {
            arrayList.add(0)
        }

        var linearLayoutManager = LinearLayoutManager(this.requireContext())
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        recycler_programs.layoutManager = linearLayoutManager

        favouriteProgramsAdapter =
            FavouriteProgramsAdapter(this.requireActivity(), openPosition, arrayList)
        recycler_programs.adapter = favouriteProgramsAdapter
    }

  /*  fun readM3UFile() {

        channelList = ArrayList()

        try {
            var assetManager = context?.resources?.assets
            var inputStream = assetManager?.open("us.m3u")
            var parser = M3UParser()
            var playList = parser.parseFile(inputStream)

            repeat(playList.playlistItems!!.size) {
                if (!playList.playlistItems!!.get(it).itemName.isNullOrEmpty()) channelList.add(
                    playList.playlistItems!![it]
                )
            }

            setUpChannelRecyclerView(channelList)
        } catch (e: Exception) {
            Log.e("parse_exception", e.message)
        }
    }*/

    override fun onChannelClicked(position: Int) {

    }

    /*
   ==================================================================================
   * Parse Channel TimeLine from EPG (XML)
   ==================================================================================
   */
    private fun parseTimeLine() {

        var xmlPullParserFactory: XmlPullParserFactory
        var programme = Programme()

        try {

            xmlPullParserFactory = XmlPullParserFactory.newInstance()
            var xmlPullParser = xmlPullParserFactory.newPullParser()

            var inputStream = context?.resources?.assets?.open("guide.xml")

            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            xmlPullParser.setInput(inputStream, null)
            xmlPullParser.nextTag()

            xmlPullParser.require(XmlPullParser.START_TAG, null, "tv")

            while (xmlPullParser.next() != XmlPullParser.END_TAG) {

                name = null

                if (xmlPullParser.eventType != XmlPullParser.START_TAG) {
                    continue
                }

                if (xmlPullParser.name == "programme") {

                     xmlPullParser.require(XmlPullParser.START_TAG, null, "programme")

                    var name: String? = null

                     name = xmlPullParser.getAttributeValue(null, "channel")
                     startDate = xmlPullParser.getAttributeValue(null, "start")
                     stopDate = xmlPullParser.getAttributeValue(null, "stop")

                    while (xmlPullParser.next() != XmlPullParser.END_TAG) {
                        if (xmlPullParser.eventType != XmlPullParser.START_TAG) {
                            continue
                        }

                        var tag = xmlPullParser.name

                        when (xmlPullParser.name) {

                            "title" -> {
                                title = readTitle(xmlPullParser)
                                Log.e("title", title)
                            }

                            "sub-title" -> {
                                sub_title = readSubTitle(xmlPullParser)
                            }

                            "desc" -> {
                                desc = readDesc(xmlPullParser)
                            }

                            "category" -> {
                                category = readCategory(xmlPullParser)
                            }

                            else -> skip(xmlPullParser)
                        }
                    }

                } else {
                    skip(xmlPullParser)
                }

                if (epgMap?.containsKey(name)!!) {

                    programme.title = title
                    programme.category = category
                    programme.desc = desc
                    programme.startDate = startDate
                    programme.stopDate = stopDate
                    programme.sub_title = sub_title

                    var list = epgMap?.get(name)
                    list?.add(programme)

                    epgMap?.replace(name!!, list!!)
                }
            }
        } catch (e: java.lang.Exception) {
            Log.e("parsing_error -----> ", e.message!!)
        }
    }

    /*
   ==================================================================================
   * Parse Channel from EPG (XML)
   ==================================================================================
   */

    //parsing xml
    private fun parseXMLFile() {

        var xmlPullParserFactory: XmlPullParserFactory

        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance()
            var xmlPullParser = xmlPullParserFactory.newPullParser()

            var inputStream = context?.resources?.assets?.open("guide.xml")

            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            xmlPullParser.setInput(inputStream, null)
            xmlPullParser.nextTag()

            var eventType = xmlPullParser.eventType

            xmlPullParser.require(XmlPullParser.START_TAG, null, "tv")

            while (xmlPullParser.next() != XmlPullParser.END_TAG) {

                if (channelList.size == 10) break

                if (xmlPullParser.eventType != XmlPullParser.START_TAG) {
                    continue
                }
                // Starts by looking for the entry tag
                if (xmlPullParser.name == "channel") {

                    xmlPullParser.require(XmlPullParser.START_TAG, null, "channel")

                    while (xmlPullParser.next() != XmlPullParser.END_TAG) {
                        if (xmlPullParser.eventType != XmlPullParser.START_TAG) {
                            continue
                        }
                        when (xmlPullParser.name) {
                            "display-name" -> {
                                display_name = readDisplayName(xmlPullParser)
                                //   Log.e("disply_name", display_name)
                            }

                            "url" -> {
                                url = readUrl(xmlPullParser)
                                //   Log.e("url", url)
                            }

                            "icon" -> {
                                icon = readIcon(xmlPullParser)
                                //  Log.e("icon", icon)
                            }

                            else -> skip(xmlPullParser)
                        }
                    }
                } else {

                }

                var item = M3UItem()
                item.itemName = display_name
                item.itemUrl = url
                item.itemIcon = icon

                epgMap?.set(display_name!!, programmeList)

                channelList.add(item)
            }

             parseTimeLine()
             setUpChannelRecyclerView(channelList)
        } catch (e: Exception) {
            Log.e("parsing_error -----> ", e.message!!)
        }
    }

    /*
    ==================================================================================
    * XML reader methods
    ==================================================================================
    */

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readDisplayName(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, null, "display-name")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, null, "display-name")
        return title
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTitle(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, null, "title")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, null, "title")
        return title
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readSubTitle(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, null, "sub-title")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, null, "sub-title")
        return title
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readDesc(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, null, "desc")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, null, "desc")
        return title
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readCategory(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, null, "category")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, null, "category")
        return title
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readIcon(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, null, "icon")

        val tag = parser.name

        val iconLink = parser.getAttributeValue(null, "src")
        parser.nextTag()

        parser.require(XmlPullParser.END_TAG, null, "icon")
        return iconLink
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readUrl(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, null, "url")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, null, "url")
        return title
    }


    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}