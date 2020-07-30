package com.hash.cotinum.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.cotinum.R
import com.hash.cotinum.adapters.CastsAdapter
import kotlinx.android.synthetic.main.fragment_record.*

class RecordFragment : Fragment() {

    private lateinit var castsAdapter: CastsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linear_cancel.setOnClickListener {

        }

        setUpCasts()
    }

    private fun setUpCasts() {
        var linearLayoutManager = LinearLayoutManager(this.requireContext())
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        recycler_casts.layoutManager = linearLayoutManager

        castsAdapter = CastsAdapter(this.requireContext())
        recycler_casts.adapter = castsAdapter
    }
}