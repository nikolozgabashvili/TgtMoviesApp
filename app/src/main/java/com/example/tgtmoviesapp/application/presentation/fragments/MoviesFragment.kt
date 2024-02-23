package com.example.tgtmoviesapp.application.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tgtmoviesapp.application.domain.models.MovieModelIndicator
import com.example.tgtmoviesapp.application.domain.models.PopularMovies
import com.example.tgtmoviesapp.application.domain.models.UpcomingMovies
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.MovieAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.MoviesViewModel
import com.example.tgtmoviesapp.databinding.FragmentMoviesBinding
import kotlinx.coroutines.launch


class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!


    private lateinit var popularAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView

    private val mainViewModel: MoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
            mainViewModel.popularMoviesSuccess.collect {
                it?.let {
                    updatePopularAdapter(it)
                    println(it)
                }
            }
        }

        lifecycleScope.launch {
            mainViewModel.upcomingMoviesSuccess.collect {
                it?.let {
                    updateUpcomingAdapter(it)
                }
                println(it)
            }
        }
    }

    private fun updatePopularAdapter(data: PopularMovies) {
        popularAdapter.setMovieList(data,MovieModelIndicator.POPULAR_MOVIE)


    }
    private fun updateUpcomingAdapter(data: UpcomingMovies) {
        upcomingAdapter.setMovieList(data,MovieModelIndicator.UPCOMING_MOVIE)


    }

    private fun initAdapters() {
        initPopularAdapter()
        initUpcomingAdapter()


    }

    private fun initUpcomingAdapter() {
        upcomingAdapter = MovieAdapter()
        recyclerView = binding.upcomingMoviesRecycler
        recyclerView.adapter = upcomingAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    private fun initPopularAdapter() {
        popularAdapter = MovieAdapter()
        recyclerView = binding.popularMovieRecycler
        recyclerView.adapter = popularAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}