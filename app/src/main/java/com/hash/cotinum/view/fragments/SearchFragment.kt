package com.hash.cotinum.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.adapters.ChannelsAdapter
import com.hash.cotinum.adapters.ShowsAdapter
import com.hash.cotinum.constants.Constants.Companion.posterlist
import com.hash.cotinum.view.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_guide.*

class SearchFragment : Fragment(), ShowsAdapter.onItemClick {

    private lateinit var showsAdapter: ShowsAdapter
    private lateinit var channelsAdapter: ChannelsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView(recycler_popular)
        setUpChannelsRecyclerView(recycler_channels)
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = linearLayoutManager

        showsAdapter = ShowsAdapter(this.requireActivity(), posterlist, this)
        recyclerView.adapter = showsAdapter
    }

    private fun setUpChannelsRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = linearLayoutManager

        channelsAdapter = ChannelsAdapter(this.requireActivity(), posterlist)
        recyclerView.adapter = channelsAdapter
    }

    override fun onClick(position: Int) {
        //TODO : To be implemented
    }
}