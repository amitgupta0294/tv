package com.hash.cotinum.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.adapters.EpisodeAdapter
import com.hash.cotinum.adapters.SeriesPriorityAdapter
import kotlinx.android.synthetic.main.fragment_series_priority.*

class SeriesPriorityFragment : Fragment() {

    private lateinit var seriesPriorityAdapter: SeriesPriorityAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_series_priority, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView(recycler_series_priority)
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        seriesPriorityAdapter = SeriesPriorityAdapter(this.requireActivity())
        recyclerView.adapter = seriesPriorityAdapter
    }
}