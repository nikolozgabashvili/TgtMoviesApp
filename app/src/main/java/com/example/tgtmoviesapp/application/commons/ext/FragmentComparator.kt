package com.example.tgtmoviesapp.application.commons.ext

import androidx.fragment.app.Fragment
import com.example.tgtmoviesapp.application.presentation.fragments.celebrities.CelebritiesFragment
import com.example.tgtmoviesapp.application.presentation.fragments.celebrities.FoundCelebritiesFragment
import com.example.tgtmoviesapp.application.presentation.fragments.movies.FoundMoviesFragment
import com.example.tgtmoviesapp.application.presentation.fragments.movies.MoviesFragment
import com.example.tgtmoviesapp.application.presentation.fragments.tvShows.FoundShowsFragment
import com.example.tgtmoviesapp.application.presentation.fragments.tvShows.TvShowFragment

class FragmentComparator : Comparator<Fragment?> {
    override fun compare(fragment1: Fragment?, fragment2: Fragment?): Int {
        return when {
            fragment1 is FoundMoviesFragment && fragment2 is FoundShowsFragment -> -1 // fragment2 before fragment3
            fragment1 is FoundShowsFragment && fragment2 is FoundCelebritiesFragment -> -1 // fragment3 before fragment1
            fragment1 is FoundCelebritiesFragment && fragment2 is FoundMoviesFragment -> 1 // fragment2 before fragment1
            else -> 0
        }
    }
}