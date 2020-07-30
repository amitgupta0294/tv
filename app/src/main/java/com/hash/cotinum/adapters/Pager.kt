package com.hash.cotinum.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hash.cotinum.view.fragments.AppsFragment
import com.hash.cotinum.view.fragments.DVRFragment
import com.hash.cotinum.view.fragments.SearchFragment
import com.hash.cotinum.view.fragments.OnDemandFragment

class Pager(var fm: FragmentManager?, var count: Int?) : FragmentStatePagerAdapter(fm!!) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                SearchFragment()
            }
            1 -> {
                DVRFragment()
            }
            2 -> {
                OnDemandFragment()
            }
            else -> {
                AppsFragment()
            }

        }
    }

    override fun getCount(): Int {
        return count!!
    }
}