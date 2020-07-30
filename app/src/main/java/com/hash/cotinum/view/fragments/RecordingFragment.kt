package com.hash.cotinum.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.adapters.CategoriesAdapter
import com.hash.cotinum.adapters.EpisodeAdapter
import com.hash.cotinum.adapters.ShowsAdapter
import com.hash.cotinum.constants.Constants
import kotlinx.android.synthetic.main.fragment_recording.*

class RecordingFragment : Fragment(), ShowsAdapter.onItemClick {

    private lateinit var showsAdapter: ShowsAdapter
    private lateinit var episodeAdapter: EpisodeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recording, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView(recycler_recordings)
        setUpEpisodeRecyclerView(recycler_episodes)
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = linearLayoutManager

        showsAdapter = ShowsAdapter(this.requireActivity(), Constants.posterlist, this)
        recyclerView.adapter = showsAdapter
    }

    private fun setUpEpisodeRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        episodeAdapter = EpisodeAdapter(this.requireActivity())
        recyclerView.adapter = episodeAdapter
    }

    override fun onClick(position: Int) {

    }

}