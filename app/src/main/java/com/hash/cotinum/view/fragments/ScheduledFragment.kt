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
import com.hash.cotinum.adapters.ScheduledAdapter
import com.hash.cotinum.view.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_scheduled.*

class ScheduledFragment : Fragment(), ScheduledAdapter.onButtonClick {

    private lateinit var scheduledAdapter: ScheduledAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scheduled, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpEpisodeRecyclerView(recycler_schedule)
    }

    private fun setUpEpisodeRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        scheduledAdapter = ScheduledAdapter(this.requireActivity(), this)
        recyclerView.adapter = scheduledAdapter
    }

    override fun onTapped(position: Int) {
        val ft = (parentFragment as DVRFragment).childFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        ft.replace(R.id.linear_fragment_container, ScheduledItemDetailsFragment(), "id1").addToBackStack(null).commit()
    }
}