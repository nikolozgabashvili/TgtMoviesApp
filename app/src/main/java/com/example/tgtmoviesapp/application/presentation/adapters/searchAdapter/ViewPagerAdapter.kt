package com.example.tgtmoviesapp.application.presentation.adapters.searchAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tgtmoviesapp.application.domain.models.IndicatorForViewPager
import com.example.tgtmoviesapp.application.presentation.fragments.FoundCelebritiesFragment
import com.example.tgtmoviesapp.application.presentation.fragments.FoundMoviesFragment
import com.example.tgtmoviesapp.application.presentation.fragments.FoundShowsFragment
import com.example.tgtmoviesapp.application.presentation.fragments.FoundThingsFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var itemCount = 0
    private var list: Array<List<Any>> = emptyArray()
    fun updateAdapter(list: Array<List<Any>>) {
        this.list = list

    }

    override fun getItemCount(): Int {
        itemCount = 0

        if (list.all {
            it.isNotEmpty()
            })
        {
            itemCount = 4
        }
        list.map {
            //TODO aq rame unda movifiqroooo
        }
        return itemCount
    }

    override fun createFragment(position: Int): Fragment {
        return FoundCelebritiesFragment()

    }

    fun getPageTitle(position: Int): String {
        return ""
    }
}