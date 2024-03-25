package com.example.tgtmoviesapp.application.presentation.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.ext.addInterceptor
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.presentation.adapters.celebritiesAdapter.CelebritiesAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.movieAdapters.MovieAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters.TvShowsAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.SearchViewModel
import com.example.tgtmoviesapp.databinding.FragmentFoundThingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoundThingsFragment : Fragment() {

    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var movieRecyclerView: RecyclerView

    private lateinit var tvShowsAdapter: TvShowsAdapter
    private lateinit var tvShowsRecyclerView: RecyclerView

    private lateinit var peopleAdapter: CelebritiesAdapter
    private lateinit var peopleRecycler: RecyclerView

    private val searchViewModel: SearchViewModel by viewModels()
    private var query:String = ""
    private lateinit var searchView:EditText


    private var _binding: FragmentFoundThingsBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoundThingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = requireActivity().findViewById(R.id.searchView)
        query = searchView.text.toString()

        initAdapters()
        setupObservers()

        binding.seemoviesTxt.setOnClickListener {
            requireActivity().findViewById<ViewPager2>(R.id.viewpager).currentItem = 1
        }
        binding.seeTvShows.setOnClickListener {
            requireActivity().findViewById<ViewPager2>(R.id.viewpager).currentItem = 2
        }
        binding.seeCelebrities.setOnClickListener {
            requireActivity().findViewById<ViewPager2>(R.id.viewpager).currentItem = 3
        }

    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.submittedText.collect {
                if (it != query && query != "") {
                    searchViewModel.getEverythingByQuery(query)
                    searchViewModel.setSubmittedText(query)
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.movies.collect {
                it?.let {
                    it.data?.let { movies ->
                        updateMoviesAdapter(movies)


                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.tvShows.collect {
                it?.let {
                    it.data?.let { movies ->
                        updateTvShowsAdapter(movies)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.people.collect {
                it?.let {
                    it.data?.let { movies ->
                        updatePeopleAdapter(movies)

                    }
                }
            }
        }

    }





    private fun updatePeopleAdapter(movies: Person) {
        movies.results?.let {
            peopleAdapter.setCelebList(it)
        }

    }

    private fun updateTvShowsAdapter(shows: TvShows) {
        shows.results?.let {

            tvShowsAdapter.setShowList(shows.results, DisplayIndicator.WIDE_IMAGE)
        }
    }

    private fun updateMoviesAdapter(movies: Movies) {
        movies.results?.let {
            moviesAdapter.setMovieList(movies.results)
        }

    }


    private fun initAdapters() {
        peopleAdapter = CelebritiesAdapter()
        peopleRecycler = binding.celebritiesRecycler
        peopleRecycler.adapter = peopleAdapter
        peopleRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        peopleAdapter.onClick = {
            if (it != null) {
                val action =
                    SecondSearchFragmentDirections.actionSecondSearchFragmentToCelebrityDetailsFragment(
                        it
                    )
                findNavController().navigate(action)
            }
        }

        peopleRecycler.addInterceptor()
        tvShowsAdapter = TvShowsAdapter()
        tvShowsRecyclerView = binding.tvShowsRecycler
        tvShowsRecyclerView.adapter = tvShowsAdapter
        tvShowsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        tvShowsAdapter.onItemClick = {
            if (it != null) {
                val action =
                    SecondSearchFragmentDirections.actionSecondSearchFragmentToTvDetailsFragment(
                        it
                    )
                findNavController().navigate(action)
            }
        }

        tvShowsRecyclerView.addInterceptor()

        moviesAdapter = MovieAdapter()
        movieRecyclerView = binding.moviesRecycler
        movieRecyclerView.adapter = moviesAdapter
        movieRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        movieRecyclerView.addInterceptor()

        moviesAdapter.onItemClick = {
            if (it != null) {
                val action =
                    SecondSearchFragmentDirections.actionSecondSearchFragmentToMovieDetailsFragment(
                        it
                    )
                findNavController().navigate(action)
            }
        }

    }


}


