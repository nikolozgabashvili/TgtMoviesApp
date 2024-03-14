package com.example.tgtmoviesapp.application.presentation.adapters.searchAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tgtmoviesapp.application.presentation.fragments.movies.FoundMoviesFragment
import com.example.tgtmoviesapp.application.presentation.fragments.search.FoundThingsFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var movieSuccess : List<Fragment> = emptyList()
    private var headers:List<String> = emptyList()
    private var itemCount = 0

    fun updateAdapter(movieSuccess: List<Fragment>, sortedHeaderList: List<String>) {

        this.movieSuccess = movieSuccess
        this.headers = sortedHeaderList

        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        itemCount = if (movieSuccess.size ==3) 4 else movieSuccess.size
        return itemCount
    }



    override fun createFragment(position: Int): Fragment {
        return when(position in 0..itemCount){
            true ->movieSuccess[position]
            false -> FoundThingsFragment()
        }


    }

    fun getPageTitle(position: Int): String {
        return headers[position]
    }
}