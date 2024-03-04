package com.example.tgtmoviesapp.application.presentation.adapters.searchAdapter

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tgtmoviesapp.application.presentation.fragments.FoundCelebritiesFragment
import com.example.tgtmoviesapp.application.presentation.fragments.FoundMoviesFragment
import com.example.tgtmoviesapp.application.presentation.fragments.FoundShowsFragment
import com.example.tgtmoviesapp.application.presentation.fragments.FoundThingsFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var itemCount = 0
    private var movieSuccess = false
    private var tvSuccess = false
    private var celebritySuccess = false

    fun updateAdapter(movieSuccess: Boolean, tvSuccess: Boolean, celebritySuccess: Boolean) {

        this.movieSuccess = movieSuccess
        this.tvSuccess = tvSuccess
        this.celebritySuccess = celebritySuccess


        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        itemCount = if (tvSuccess && movieSuccess && celebritySuccess) {
            4
        } else if (tvSuccess && movieSuccess) {
            2
        } else if (tvSuccess && celebritySuccess) {
            2
        } else if (movieSuccess && celebritySuccess) {
            2
        } else if (!tvSuccess && !movieSuccess && !celebritySuccess) {
            0
        } else {
            1
        }

        return itemCount
    }



    override fun createFragment(position: Int): Fragment {

        val fragment = if (itemCount == 4) {
             when (position) {
                0 -> FoundThingsFragment()
                1 -> FoundMoviesFragment()
                2 -> FoundShowsFragment()
                else -> FoundCelebritiesFragment()
            }
        } else if (itemCount == 2 && !celebritySuccess) {
             when (position) {
                0 -> FoundMoviesFragment()
                else -> FoundShowsFragment()
            }
        } else if (itemCount == 2 && !tvSuccess) {
             when (position) {
                0 -> FoundMoviesFragment()
                else -> FoundCelebritiesFragment()
            }
        } else if (itemCount == 2 && !movieSuccess) {
             when (position) {
                0 -> FoundShowsFragment()
                else -> FoundCelebritiesFragment()
            }
        } else if (movieSuccess)
             FoundMoviesFragment()
        else if (tvSuccess)
             FoundShowsFragment()
        else if (celebritySuccess)
             FoundCelebritiesFragment()
        else
             FoundCelebritiesFragment()

        return fragment

    }

    fun getPageTitle(position: Int): String {
        if (itemCount == 4) {
            return when (position) {
                0 -> "all"
                1 -> "FoundMoviesFragment()"
                2 -> "FoundShowsFragment()"
                else -> "FoundCelebritiesFragment()"
            }
        } else if (itemCount == 2 && !celebritySuccess) {
            return when (position) {
                0 -> "FoundMoviesFragment()"
                else -> "FoundShowsFragment()"
            }
        } else if (itemCount == 2 && !tvSuccess) {
            return when (position) {
                0 -> "FoundMoviesFragment()"
                else -> "FoundCelebritiesFragment()"
            }
        } else if (itemCount == 2 && !movieSuccess) {
            return when (position) {
                0 -> "FoundShowsFragment()"
                else ->" FoundCelebritiesFragment()"
            }
        } else if (movieSuccess)
            return ""
        else if (tvSuccess)
            return ""
        else if (celebritySuccess)
            return ""
        else
            return ""
    }
}