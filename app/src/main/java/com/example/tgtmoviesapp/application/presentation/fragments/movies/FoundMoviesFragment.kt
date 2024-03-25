package com.example.tgtmoviesapp.application.presentation.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.presentation.adapters.movieAdapters.TopRatedMoviesAdapter
import com.example.tgtmoviesapp.application.presentation.fragments.search.SecondSearchFragment
import com.example.tgtmoviesapp.application.presentation.fragments.search.SecondSearchFragmentDirections
import com.example.tgtmoviesapp.application.presentation.viewModels.CelebrityDetailsViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.DetailsViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.MoviesViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.SearchViewModel
import com.example.tgtmoviesapp.databinding.FragmentFoundMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.IllegalFormatCodePointException

@AndroidEntryPoint
class FoundMoviesFragment : Fragment() {

    private var _binding: FragmentFoundMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: TopRatedMoviesAdapter
    private lateinit var movieRecyclerView: RecyclerView

    private var movieType: String? = "NONE"
    private val celebrityDetailsViewModel: CelebrityDetailsViewModel by viewModels()
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    private var infoId: Int = -1
    private var currentPage = 1

    private var currentMovieList: List<Movies.Result?> = mutableListOf()
    private val moviesViewModel: MoviesViewModel by activityViewModels()

    private var requestNextPage = true
    private var query:String = ""

    private lateinit var searchView:EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoundMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        query = ""
        try {
            searchView = requireActivity().findViewById(R.id.searchView)
            query = searchView.text.toString()
        }catch (_:Exception){}
        movieType = arguments?.getString("movieType", "NONE") ?: "NONE"
        infoId = arguments?.getInt("id", -1) ?: -1
        binding.movieType.text = movieType
        binding.backButton.setOnClickListener { findNavController().popBackStack() }
        initAdapter()
        setupObserver()

    }

    private fun setupObserver() {

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.submittedText.collect {
                if (it != query && query != "") {
                    searchViewModel.getEverythingByQuery(query)
                    searchViewModel.setSubmittedText(query)
                }
            }
        }


        if (movieType == "NONE") {
            binding.header.visibility = View.GONE
            viewLifecycleOwner.lifecycleScope.launch {

                searchViewModel.moviesPaged.collect {
                    it?.let {
                        it.data?.let { movies ->

                            if (movies.page == 1) {
                                currentMovieList = emptyList()
                                currentPage = 1
                            }
                            val lst = currentMovieList + movies.results as List<Movies.Result?>
                            currentMovieList = lst
                            updateMoviesAdapter(lst)
                            movies.page?.let {
                                movies.totalPages?.let {
                                    if (movies.page < movies.totalPages) {
                                        delay(2)
                                        requestNextPage = true
                                    }
                                }
                            }


                        }
                    }
                }
            }
        } else if (movieType == "Recommended") {
            binding.header.visibility = View.VISIBLE
            viewLifecycleOwner.lifecycleScope.launch {
                detailsViewModel.recommended.collect {
                    if (it == null) {
                        detailsViewModel.getRecommendedMovies(infoId, 1)
                    } else {
                        it.data?.let { movies ->

                            if (movies.page == 1) {
                                currentMovieList = emptyList()
                                currentPage = 1
                            }
                            val lst = currentMovieList + movies.results as List<Movies.Result?>
                            currentMovieList = lst
                            updateMoviesAdapter(lst)
                            movies.page?.let {
                                movies.totalPages?.let {
                                    if (movies.page < movies.totalPages) {
                                        delay(2)
                                        requestNextPage = true
                                    }
                                }
                            }

                        }
                    }
                }
            }


        } else if (movieType == "Similar") {
            binding.header.visibility = View.VISIBLE
            viewLifecycleOwner.lifecycleScope.launch {
                detailsViewModel.similarMovies.collect {
                    if (it == null) {
                        detailsViewModel.getSimilarMovies(infoId, 1)
                    } else {
                        it.data?.let { movies ->

                            if (movies.page == 1) {
                                currentMovieList = emptyList()
                                currentPage = 1
                            }
                            val lst = currentMovieList + movies.results as List<Movies.Result?>
                            currentMovieList = lst
                            updateMoviesAdapter(lst)
                            movies.page?.let {
                                movies.totalPages?.let {
                                    if (movies.page < movies.totalPages) {
                                        delay(2)
                                        requestNextPage = true
                                    }
                                }
                            }

                        }
                    }
                }
            }
        } else if (movieType != "Movies") {
            binding.header.visibility = View.VISIBLE

            viewLifecycleOwner.lifecycleScope.launch {
                moviesViewModel.moviePaging.collect {
                    it?.let {
                        it.data?.let { movies ->

                            if (movies.page == 1) {
                                currentMovieList = emptyList()
                                currentPage = 1
                            }
                            val lst = currentMovieList + movies.results as List<Movies.Result?>
                            currentMovieList = lst
                            updateMoviesAdapter(lst)
                            movies.page?.let {
                                movies.totalPages?.let {
                                    if (movies.page < movies.totalPages) {
                                        delay(2)
                                        requestNextPage = true
                                    }
                                }
                            }

                        }
                    }
                }
            }
        } else {
            binding.header.visibility = View.VISIBLE
            viewLifecycleOwner.lifecycleScope.launch {
                celebrityDetailsViewModel.movies.collect {
                    if (it == null) {
                        celebrityDetailsViewModel.getMovies(infoId)
                    }
                    it?.data?.let {
                        updateMoviesAdapter(it.results)
                    }
                }
            }
        }


    }

    private fun initAdapter() {
        moviesAdapter = TopRatedMoviesAdapter()
        movieRecyclerView = binding.recycler
        movieRecyclerView.adapter = moviesAdapter

        movieRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                val layoutManager = recyclerView.layoutManager
                val visibleItemCount = layoutManager?.childCount ?: 0
                val totalItemCount = layoutManager?.itemCount ?: 0
                val firstVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && requestNextPage) {
                    when (movieType) {
                        "NONE" -> {
                            searchViewModel.searchMovieByPage(query, ++currentPage)
                        }

                        "Popular" -> {

                            moviesViewModel.getPopularMovieByPage(++currentPage)
                        }

                        "Trending" -> {
                            moviesViewModel.getTrendingMovieByPage(++currentPage)
                        }

                        "Playing In Theatres" -> {
                            moviesViewModel.getPITMovieByPage(++currentPage)
                        }

                        "Upcoming" -> {
                            moviesViewModel.getUpcomingMovieByPage(++currentPage)
                        }

                        "Top Rated" -> {
                            moviesViewModel.getTopRatedMovieByPage(++currentPage)
                        }
                        "Similar"->{
                            detailsViewModel.getSimilarMovies(infoId,++currentPage)
                        }
                        "Recommended"->{
                            detailsViewModel.getRecommendedMovies(infoId,++currentPage)
                        }
                    }
                    requestNextPage = false

                }
            }
        })

        moviesAdapter.onItemClick = {
            if (it != null) {
                try {
                    val action =
                        FoundMoviesFragmentDirections.actionFoundMoviesFragmentToMovieDetailsFragment(
                            it
                        )
                    findNavController().navigate(action)
                } catch (e: Exception) {
                    val action =
                        SecondSearchFragmentDirections.actionSecondSearchFragmentToMovieDetailsFragment(
                            it
                        )
                    findNavController().navigate(action)
                }
            }
        }


    }

    private fun updateMoviesAdapter(movies: List<Movies.Result?>?) {
        movies?.let {
            moviesAdapter.setMovieList(movies, DisplayIndicator.FOUND_IMAGE_TYPE)
        }

    }

    private fun updateGenreAdapters(lst: List<Genre?>) {
        moviesAdapter.setMovieGenres(lst)
    }


}