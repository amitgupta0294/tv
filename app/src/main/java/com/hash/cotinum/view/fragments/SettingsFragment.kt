package com.hash.cotinum.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.adapters.*
import com.hash.cotinum.model.AppearanceModel
import com.hash.cotinum.model.SettingsModel
import com.hash.cotinum.view.OnFragmentBackClicked
import com.hash.cotinum.view.activities.HomeActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(), SettingsAdapter.ClickListener, OnFragmentBackClicked, AppearanceSettingsAdapter.onAppearanceItemClick {

    private val settingsList = arrayListOf<String>("My Devices", "General", "EPG", "Appearance",
        "Playback", "Remote Control", "Parental Control", "Linked Devices", "Reminder", "About")

    private val generalSettingsList = arrayListOf<String>("Turn on last channel on start", "Confirm exit by press back", "Backup data", "Restore data")
    private val epgSettingsList = arrayListOf<String>("Past days to keep EPG", "Update Interval, hours", "Update on app start", "Update on playlist change", "Update EPG")
    private val languageList = arrayListOf<String>("System", "English", "Spanish")
    private val fontSizeList = arrayListOf<String>("Small", "Medium", "Large", "Very Large")
    private val remoteControlList = arrayListOf<String>("Use Left/Right for seeking while watching catch-up",
        "Use Left to rewind live stream with catch-up",
        "Use RW/FF/Pause for seeking/ Pause while watching",
        "Use RW to rewind live stream with catch-up")


    private var appearanceList = arrayListOf<AppearanceModel>()
    private var playbackOptionsList = arrayListOf<SettingsModel>()
    private var groupOptionsList = arrayListOf<SettingsModel>()
    private var tvGuideOptionsList = arrayListOf<SettingsModel>()
    private var playerOptionsList = arrayListOf<SettingsModel>()
    private var remindersList = arrayListOf<SettingsModel>()
    private var linkedDevicesList = arrayListOf<SettingsModel>()
    private var parentControlList = arrayListOf<SettingsModel>()

    private lateinit var settingsAdapter: SettingsAdapter
    private lateinit var myDevicesAdapter: MyDevicesAdapter
    private lateinit var generalSettingsAdapter: GeneralSettingsAdapter
    private lateinit var epgSettingsAdapter: EPGSettingsAdapter
    private lateinit var appearanceSettingsAdapter: AppearanceSettingsAdapter
    private lateinit var languageSettingsAdapter: LanguageSettingsAdapter
    private lateinit var playbackSettingsAdapter: PlaybackSettingsAdapter
    private lateinit var remoteControlSettingsAdapter: RemoteControlSettingsAdapter
    private lateinit var groupSettingsAdapter: GroupSettingsAdapter
    private lateinit var tvOptionsSettingsAdapter: TvOptionsSettingsAdapter
    private lateinit var playerSettingsAdapter: PlayerSettingsAdapter
    private lateinit var remindersSettingsAdapter: RemindersSettingsAdapter
    private lateinit var linkedDevicesAdapter: LinkedDevicesSettingsAdapter
    private lateinit var parentalControlSettingsAdapter: ParentalControlSettingsAdapter

    private var previousAdapter : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSettingsRecyclerView(recycler_settings)

        appearanceList.add(AppearanceModel("Groups", ""))
        appearanceList.add(AppearanceModel("TV Guide", ""))
        appearanceList.add(AppearanceModel("Player", ""))
        appearanceList.add(AppearanceModel("Language", "System"))
        appearanceList.add(AppearanceModel("Font Size", "Medium"))
    }

    private fun preparePlaybackOptionsList() {
        playbackOptionsList.add(SettingsModel("Buffer Size", "Normal"))
        playbackOptionsList.add(SettingsModel("Audio Decoder", "Hardware"))
        playbackOptionsList.add(SettingsModel("Video Decoder", "Hardware"))
        playbackOptionsList.add(SettingsModel("Auto Frame Rate (AFR)", "off"))
        playbackOptionsList.add(SettingsModel("Audio passthrough", "", true))
    }

    private fun groupOptionsList() {
        groupOptionsList.add(SettingsModel("Group sorting", "by order in playlist"))
        groupOptionsList.add(SettingsModel("Show \"All channels\" category", "", true))
        groupOptionsList.add(SettingsModel("Show \"Favorited\" category", "", true))
    }

    private fun tvGuideOptionsList() {
        tvGuideOptionsList.add(SettingsModel("Preview", "On"))
        tvGuideOptionsList.add(SettingsModel("Channels sorting", "Channels sorting", false))
        tvGuideOptionsList.add(SettingsModel("Show channel number", "", true))
        tvGuideOptionsList.add(SettingsModel("Show catch-up icon in list", "", true))
        tvGuideOptionsList.add(SettingsModel("Highlight current channel in colour", "", true))
    }

    private fun playerOptionsList() {
        playerOptionsList.add(SettingsModel("Channels list", ""))
        playerOptionsList.add(SettingsModel("Info panel", "", false))
        playerOptionsList.add(SettingsModel("History/Recent Channels", "", false))
        playerOptionsList.add(SettingsModel("Menu", "", false))
        playerOptionsList.add(SettingsModel("Clock", "On", false))
        playerOptionsList.add(SettingsModel("Panels timeout, sec", "5", false))
        playerOptionsList.add(SettingsModel("Skip step for bar", "1 min", false))
        playerOptionsList.add(SettingsModel("Skip step for RW/FF buttons", "40 sec", false))
    }

    private fun remindersOptionsList() {
        remindersList.add(SettingsModel("Reminder before program start, min", ""))
        remindersList.add(SettingsModel("Popup timeout, sec", "10"))
        remindersList.add(SettingsModel("Default action", "Watch"))
        remindersList.add(SettingsModel("Wake up from sleep mode", "May not work on all devices", true))
    }

    private fun linkedDevicesOptionsList() {
        linkedDevicesList.add(SettingsModel("Samsung Android TV", "Device ID - XJMK34KM"))
        linkedDevicesList.add(SettingsModel("MI TV", "Device ID - MKJNH679MK"))
    }

    private fun parentalControlOptionsList() {
        parentControlList.add(SettingsModel("Change PIN", ""))
        parentControlList.add(SettingsModel("PIN input method", "Picker"))
        parentControlList.add(SettingsModel("Don't require PIN after unlocking", "Always"))
        parentControlList.add(SettingsModel("Don't require for channels only", "", true))
        parentControlList.add(SettingsModel("Require PIN for", "", false, true))

        parentControlList.add(SettingsModel("Settings", "", true))
        parentControlList.add(SettingsModel("Group options", "", true))
        parentControlList.add(SettingsModel("Channel options", "", true))
    }

    override fun OnItemClick(position: Int) {

        previousAdapter = "Settings"

        if (position == 0) {
            text_settings.text = "My Devices"
            recycler_settings.adapter = null
            setUpMyDevicesRecyclerView(recycler_settings)
        } else if (position == 1) {
            text_settings.text = "General"
            recycler_settings.adapter = null
            setUpGeneralRecyclerView(recycler_settings)
        } else if (position == 2) {
            text_settings.text = "EPG"
            recycler_settings.adapter = null
            setUpEPGRecyclerView(recycler_settings)
        } else if (position == 3) {
            text_settings.text = "Appearance"
            recycler_settings.adapter = null
            setUpAppearanceRecyclerView(recycler_settings)
        } else if (position == 4) {
            text_settings.text = "Playback"
            recycler_settings.adapter = null
            setUpPlaybackRecyclerView(recycler_settings)
        } else if (position == 5) {
            text_settings.text = "Remote Control"
            recycler_settings.adapter = null
            setUpRemoteControlRecyclerView(recycler_settings)
        } else if (position == 6) {
            text_settings.text = "Parental Control"
            recycler_settings.adapter = null
            setUpParentalControlRecyclerView(recycler_settings)
        } else if (position == 7) {
            text_settings.text = "Linked Devices"
            recycler_settings.adapter = null
            setUpLinkedDevicesRecyclerView(recycler_settings)
        } else if (position == 8) {
            text_settings.text = "Reminders"
            recycler_settings.adapter = null
            setUpRemindersRecyclerView(recycler_settings)
        }
    }

    private fun setUpSettingsRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        settingsAdapter = SettingsAdapter(this.requireActivity(), settingsList, this)
        recyclerView.adapter = settingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpMyDevicesRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        myDevicesAdapter = MyDevicesAdapter(this.requireActivity())
        recyclerView.adapter = myDevicesAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpGeneralRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        generalSettingsAdapter = GeneralSettingsAdapter(this.requireActivity(), generalSettingsList)
        recyclerView.adapter = generalSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpEPGRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        epgSettingsAdapter = EPGSettingsAdapter(this.requireActivity(), epgSettingsList)
        recyclerView.adapter = epgSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpAppearanceRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        appearanceSettingsAdapter = AppearanceSettingsAdapter(this.requireActivity(), appearanceList, this)
        recyclerView.adapter = appearanceSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpLanguageRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        languageSettingsAdapter = LanguageSettingsAdapter(this.requireActivity(), languageList)
        recyclerView.adapter = languageSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpFontSizeRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        languageSettingsAdapter = LanguageSettingsAdapter(this.requireActivity(), fontSizeList)
        recyclerView.adapter = languageSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpPlaybackRecyclerView(recyclerView: RecyclerView) {

        preparePlaybackOptionsList()

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        playbackSettingsAdapter = PlaybackSettingsAdapter(this.requireActivity(), playbackOptionsList)
        recyclerView.adapter = playbackSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpRemoteControlRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        remoteControlSettingsAdapter = RemoteControlSettingsAdapter(this.requireActivity(), remoteControlList)
        recyclerView.adapter = remoteControlSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpGroupRecyclerView(recyclerView: RecyclerView) {

        groupOptionsList()

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        groupSettingsAdapter = GroupSettingsAdapter(this.requireActivity(), groupOptionsList)
        recyclerView.adapter = groupSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpTvOptionsRecyclerView(recyclerView: RecyclerView) {

        tvGuideOptionsList()

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        tvOptionsSettingsAdapter = TvOptionsSettingsAdapter(this.requireActivity(), tvGuideOptionsList)
        recyclerView.adapter = tvOptionsSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpPlayerRecyclerView(recyclerView: RecyclerView) {

        playerOptionsList()

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        playerSettingsAdapter = PlayerSettingsAdapter(this.requireActivity(), playerOptionsList)
        recyclerView.adapter = playerSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpRemindersRecyclerView(recyclerView: RecyclerView) {

        remindersOptionsList()

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        remindersSettingsAdapter = RemindersSettingsAdapter(this.requireActivity(), remindersList)
        recyclerView.adapter = remindersSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpLinkedDevicesRecyclerView(recyclerView: RecyclerView) {

        linkedDevicesOptionsList()

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        linkedDevicesAdapter = LinkedDevicesSettingsAdapter(this.requireActivity(), linkedDevicesList)
        recyclerView.adapter = linkedDevicesAdapter
        recyclerView.scheduleLayoutAnimation()
    }

    private fun setUpParentalControlRecyclerView(recyclerView: RecyclerView) {

        parentalControlOptionsList()

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        parentalControlSettingsAdapter = ParentalControlSettingsAdapter(this.requireActivity(), parentControlList)
        recyclerView.adapter = parentalControlSettingsAdapter
        recyclerView.scheduleLayoutAnimation()
    }


    override fun onBackTapped() {

        if (previousAdapter.equals("Settings", true)) {

            previousAdapter = ""

            text_settings.text = "Settings"
            recycler_settings.adapter = null
            setUpSettingsRecyclerView(recycler_settings)
        } else  if (previousAdapter.equals("Appearance", true)) {

            previousAdapter = "Settings"

            text_settings.text = "Appearance"
            recycler_settings.adapter = null
            setUpAppearanceRecyclerView(recycler_settings)
        } else {
            (activity as HomeActivity).drawer_layout.closeDrawer(GravityCompat.END)
        }
    }

    override fun onAppearanceItemTapped(position: Int) {

        previousAdapter = "Appearance"

        if (appearanceList[position].settingName == "Language") {
            text_settings.text = "Language"
            setUpLanguageRecyclerView(recycler_settings)
        } else  if (appearanceList[position].settingName == "Font Size") {
            text_settings.text = "Font Size"
            setUpFontSizeRecyclerView(recycler_settings)
        } else if (appearanceList[position].settingName == "Groups") {
            text_settings.text = "Groups"
            setUpGroupRecyclerView(recycler_settings)
        } else if (appearanceList[position].settingName == "TV Guide") {
            text_settings.text = "TV Guide"
            setUpTvOptionsRecyclerView(recycler_settings)
        } else if (appearanceList[position].settingName == "Player") {
            text_settings.text = "Player"
            setUpPlayerRecyclerView(recycler_settings)
        }
    }
}