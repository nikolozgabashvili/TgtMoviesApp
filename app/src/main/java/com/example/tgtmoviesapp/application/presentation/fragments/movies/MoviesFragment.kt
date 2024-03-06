package com.example.tgtmoviesapp.application.presentation.fragments.movies

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.presentation.adapters.movieAdapters.MovieAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.movieAdapters.TopRatedMoviesAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.MoviesViewModel
import com.example.tgtmoviesapp.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
    private lateinit var trendingRecyclerView: RecyclerView
    private lateinit var trendingAdapter: MovieAdapter



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
        setupListeners()


    }

    private fun setupListeners() {
        binding.scrollView2.setOnScrollChangeListener { _, _, scrollY, _, _ ->

            if (scrollY>0){
                binding.header.setBackgroundColor(Color.parseColor("#122727"))
            }else{
                binding.header.setBackgroundColor(Color.parseColor("#0f1c1c"))
            }

        }

        binding.seePopularMovies.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToFoundMoviesFragment("Popular")
            mainViewModel.setMoviePagingValue(mainViewModel.movies.value)
            findNavController().navigate(action)
        }
        binding.seeTrendingMovies.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToFoundMoviesFragment("Trending")
            mainViewModel.setMoviePagingValue(mainViewModel.trendingMovies.value)
            findNavController().navigate(action)
        }
        binding.seeTopRatedMovies.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToFoundMoviesFragment("Top Rated")
            mainViewModel.setMoviePagingValue(mainViewModel.topRatedMovies.value)
            findNavController().navigate(action)
        }
        binding.seePlayingInTheatreMovies.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToFoundMoviesFragment("Playing In Theatres")
            mainViewModel.setMoviePagingValue(mainViewModel.pITMovies.value)
            findNavController().navigate(action)
        }
        binding.seeUpcomingMovies.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToFoundMoviesFragment("Upcoming")
            mainViewModel.setMoviePagingValue(mainViewModel.upcomingMovies.value)
            findNavController().navigate(action)
        }

    }


    private fun setupObservers() {
        lifecycleScope.launch {
            mainViewModel.movies.collect {
                it?.let {
                    it.data?.let { data ->
                        updatePopularAdapter(data)

                    }

                }
            }
        }

        lifecycleScope.launch {
            mainViewModel.trendingMovies.collect {
                it?.let {
                    it.data?.let { data ->
                        updateTrendingAdapter(data)
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
        lifecycleScope.launch {
            mainViewModel.moviesGenres.collect {

                it?.let {resource->
                    resource.data?.let {genre->
                        genre.genres?.let {lst->

                            updateGenreAdapters(lst)
                        }
                    }
                }


            }

        }


    }

    private fun updateGenreAdapters(lst: List<Genre.Genre?>) {
        trendingAdapter.setMovieGenres(lst)
        popularAdapter.setMovieGenres(lst)
        topRatedAdapter.setMovieGenres(lst)
        upcomingAdapter.setMovieGenres(lst)
        pITAdapter.setMovieGenres(lst)
    }

    private fun updateTrendingAdapter(data: Movies) {
        data.results?.let {
            trendingAdapter.setMovieList(data.results)
        }

    }

    private fun updatePopularAdapter(data: Movies) {
        data.results?.let {
            popularAdapter.setMovieList(data.results)
        }

    }

    private fun updateUpcomingAdapter(data: Movies) {
        data.results?.let {
            upcomingAdapter.setMovieList(data.results)
        }

    }

    private fun updateTopRatedAdapter(data: Movies) {
        data.results?.let {
            topRatedAdapter.setMovieList(data.results,DisplayIndicator.WIDE_IMAGE)
        }


    }

    private fun updatePITAdapter(data: Movies) {
        data.results?.let {
            pITAdapter.setMovieList(data.results,DisplayIndicator.WIDE_IMAGE)
        }
    }


    private fun initAdapters() {
        initPopularAdapter()
        initUpcomingAdapter()
        initTopRatedAdapter()
        initPITAdapter()
        initTrendingAdapter()


    }

    private fun initTrendingAdapter() {
        trendingAdapter = MovieAdapter()
        trendingRecyclerView = binding.trendingMoviesRecycler
        trendingRecyclerView.adapter = trendingAdapter
        trendingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
            GridLayoutManager(requireContext(), 4, GridLayoutManager.HORIZONTAL, false)
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