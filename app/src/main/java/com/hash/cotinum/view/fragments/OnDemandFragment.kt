package com.hash.cotinum.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.adapters.ShowsAdapter
import com.hash.cotinum.constants.Constants.Companion.posterlist
import com.hash.cotinum.view.activities.PlayerActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_on_demand.*

class OnDemandFragment : Fragment(), ShowsAdapter.onItemClick {

    private lateinit var showsAdapter: ShowsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_on_demand, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO : Static content to be removed after implementation
        Picasso.get().load("https://mypostercollection.com/wp-content/uploads/2018/07/The-Walking-Dead-poster-4-1024x768.jpg").into(image_show_banner)

        setUpRecyclerView(recycler_popular)
        setUpRecyclerView(recycler_continue_watching)

        linear_play.setOnClickListener {
            startActivity(Intent(activity, PlayerActivity::class.java))
            activity?.overridePendingTransition(R.anim.anim_next_slide_in, R.anim.anim_next_slide_out)
        }
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = linearLayoutManager

        showsAdapter = ShowsAdapter(this.requireActivity(), posterlist, this)
        recyclerView.adapter = showsAdapter
    }

    override fun onClick(position: Int) {
        constraint_details.visibility = View.VISIBLE
    }
}