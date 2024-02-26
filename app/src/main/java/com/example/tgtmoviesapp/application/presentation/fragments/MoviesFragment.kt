package com.example.tgtmoviesapp.application.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tgtmoviesapp.application.domain.models.MovieModelIndicator
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.MovieAdapter
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.TopRatedMoviesAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.MoviesViewModel
import com.example.tgtmoviesapp.databinding.FragmentMoviesBinding
import kotlinx.coroutines.launch


class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!


    private lateinit var popularAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var topRatedAdapter: TopRatedMoviesAdapter
    private lateinit var popularRecyclerView: RecyclerView
    private lateinit var upcomingRecyclerView: RecyclerView
    private lateinit var topRatedRecyclerView: RecyclerView
    private lateinit var pITRecyclerView: RecyclerView
    private lateinit var pITAdapter: MovieAdapter

    private val mainViewModel: MoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            mainViewModel.movies.collect {
                it?.let {
                    it.data?.let { data->
                        updatePopularAdapter(data)
                    }

                }
            }
        }

        lifecycleScope.launch {
            mainViewModel.upcomingMovies.collect {
                it?.let {
                    it.data?.let {
                        updateUpcomingAdapter(it)
                    }
                }

            }
        }

        lifecycleScope.launch {
            mainViewModel.topRatedMovies.collect {
                it?.let {
                    it.data?.let {
                        updateTopRatedAdapter(it)
                    }
                }

            }
        }

        lifecycleScope.launch {
            mainViewModel.pITMovies.collect {
                it?.let {
                    it.data?.let {
                        updatePITAdapter(it)
                    }
                }

            }
        }
    }

    private fun updatePopularAdapter(data: Movies) {
        popularAdapter.setMovieList(data)

    }

    private fun updateUpcomingAdapter(data: Movies) {
        upcomingAdapter.setMovieList(data)

    }

    private fun updateTopRatedAdapter(data: Movies) {
        topRatedAdapter.setMovieList(data)

    }
    private fun updatePITAdapter(data: Movies) {
        pITAdapter.setMovieList(data,MovieModelIndicator.PIT_MOVIE)

    }


    private fun initAdapters() {
        initPopularAdapter()
        initUpcomingAdapter()
        initTopRatedAdapter()
        initPITAdapter()


    }

    private fun initPITAdapter() {
        pITAdapter = MovieAdapter()
        pITRecyclerView = binding.playingInTheatresRecycler
        pITRecyclerView.adapter = pITAdapter
        pITRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initTopRatedAdapter() {
        topRatedAdapter = TopRatedMoviesAdapter()
        topRatedRecyclerView = binding.topRatedMoviesRecyclerGrid
        topRatedRecyclerView.adapter = topRatedAdapter
        topRatedRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.HORIZONTAL, false)
    }

    private fun initUpcomingAdapter() {
        upcomingAdapter = MovieAdapter()
        upcomingRecyclerView = binding.upcomingMoviesRecycler
        upcomingRecyclerView.adapter = upcomingAdapter
        upcomingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initPopularAdapter() {
        popularAdapter = MovieAdapter()
        popularRecyclerView = binding.popularMovieRecycler
        popularRecyclerView.adapter = popularAdapter
        popularRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}