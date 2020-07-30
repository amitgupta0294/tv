package com.hash.cotinum.background

import android.util.Log
import android.util.Xml
import com.hash.cotinum.MyApplication
import com.hash.cotinum.m3uparser.M3UItem
import com.hash.cotinum.model.epg.Programme
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class CotinumXmlParser {

    var display_name: String? = null
    var icon: String? = null
    var url: String? = null

    var title: String? = null
    var sub_title: String? = null
    var desc: String? = null
    var category: String? = null
    var name: String? = null
    var stopDate: String? = null
    var startDate: String? = null

    private var epgMap = HashMap<String, ArrayList<Programme>>()
    private var programmeList = ArrayList<Programme>()

    @Throws(XmlPullParserException::class, IOException::class)
    fun parseChannels(inputStream: InputStream): ArrayList<M3UItem> {
        inputStream.use { stream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(stream, null)
            parser.nextTag()
            return readChannelsFeed(parser, inputStream)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun parseProgrammes(inputStream: InputStream): HashMap<String, ArrayList<Programme>> {

        inputStream.use { stream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(stream, null)
            parser.nextTag()
            return readProgrammeFeed(parser, inputStream)
        }
    }

    /*
        ==================================================================================
        * XML reader methods
        ==================================================================================
    */

    fun readChannelsFeed(xmlPullParser: XmlPullParser, inputStream: InputStream) : ArrayList<M3UItem> {

        var channelsList = ArrayList<M3UItem>()

        var inputStream = MyApplication.appContext.resources?.assets?.open("guide.xml")

        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        xmlPullParser.setInput(inputStream, null)
        xmlPullParser.nextTag()

        xmlPullParser.require(XmlPullParser.START_TAG, null, "tv")

        while (xmlPullParser.next() != XmlPullParser.END_TAG) {

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
                        }

                        "url" -> {
                            url = readUrl(xmlPullParser)
                        }

                        "icon" -> {
                            icon = readIcon(xmlPullParser)
                        }

                        else -> skip(xmlPullParser)
                    }
                }
            }

            var item = M3UItem()
            item.itemName = display_name
            item.itemUrl = url
            item.itemIcon = icon

            epgMap.set(display_name!!, programmeList)

            channelsList.add(item)
        }

      //  Log.e("map_channel_list ===========>", epgMap.size.toString())
        return channelsList
    }

    fun readProgrammeFeed(xmlPullParser: XmlPullParser, inputStream: InputStream) : HashMap<String, ArrayList<Programme>> {
        var programsList = ArrayList<Programme>()

        var inputStream = MyApplication.appContext.resources?.assets?.open("guide.xml")

        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        xmlPullParser.setInput(inputStream, null)
        xmlPullParser.nextTag()

        xmlPullParser.require(XmlPullParser.START_TAG, null, "tv")

        while (xmlPullParser.next() != XmlPullParser.END_TAG) {

            if (xmlPullParser.eventType != XmlPullParser.START_TAG) {
                continue
            }

            if (xmlPullParser.name == "programme") {

                xmlPullParser.require(XmlPullParser.START_TAG, null, "programme")

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
                          //  Log.e("title", title)
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

            if (epgMap.containsKey(name)) {

                var programme = Programme()
                programme.title = title
                programme.category = category
                programme.desc = desc
                programme.startDate = startDate
                programme.stopDate = stopDate
                programme.sub_title = sub_title

                var list = epgMap.get(name)

                list?.add(programme)
            }
        }

        return epgMap
    }

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