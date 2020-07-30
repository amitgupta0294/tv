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
import com.hash.cotinum.adapters.ChannelsAdapter
import kotlinx.android.synthetic.main.fragment_d_v_r.*
import kotlinx.android.synthetic.main.fragment_guide.*

class DVRFragment : Fragment(), CategoriesAdapter.ClickListener {

    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var categoriesList : ArrayList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_d_v_r, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoriesList = ArrayList()
        categoriesList.add(getString(R.string.string_recordings))
        categoriesList.add(getString(R.string.string_scheduled))
        categoriesList.add(getString(R.string.string_priority))
        categoriesList.add(getString(R.string.string_history))

        setUpChannelsRecyclerView(recycler_categories)

        val recordingFragment = RecordingFragment()
        val ft = childFragmentManager.beginTransaction()
        ft.add(R.id.linear_fragment_container, recordingFragment, "id").commit()
    }

    private fun setUpChannelsRecyclerView(recyclerView: RecyclerView) {

        var linearLayoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.layoutManager = linearLayoutManager

        categoriesAdapter = CategoriesAdapter(this.requireActivity(), categoriesList, this)
        recyclerView.adapter = categoriesAdapter
    }

    override fun OnItemClick(position: Int) {
        if (position == 0) {
            loadFragment(RecordingFragment())
        }

        if (position == 1) {
            loadFragment(ScheduledFragment())
        }

        if (position == 2) {
            loadFragment(SeriesPriorityFragment())
        }

        if (position == 3) {
            loadFragment(HistoryFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val ft = childFragmentManager.beginTransaction()
        ft.replace(R.id.linear_fragment_container, fragment, "id").commit()
    }
}