package com.example.tgtmoviesapp.application.presentation.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
import com.example.tgtmoviesapp.application.presentation.viewModels.MoviesViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.SearchViewModel
import com.example.tgtmoviesapp.databinding.FragmentFoundMoviesBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FoundMoviesFragment : Fragment() {

    private var _binding: FragmentFoundMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: TopRatedMoviesAdapter
    private lateinit var movieRecyclerView: RecyclerView

    private var movieType: String? = "NONE"

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val moviesViewModel: MoviesViewModel by activityViewModels()
    private var currentPage = 1
    private var currentMovieList: List<Movies.Result?> = mutableListOf()
    private var requestNextPage = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoundMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

//        moviesViewModel.setMoviePagingNull()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        movieType = arguments?.getString("movieType", "NONE")?:"NONE"
        binding.movieType.text = movieType
        binding.backButton.setOnClickListener{findNavController().popBackStack()}
        initAdapter()
        setupObserver()

    }

    private fun setupObserver() {


        if (movieType == "NONE") {
            binding.header.visibility=View.GONE
            viewLifecycleOwner.lifecycleScope.launch {

                searchViewModel.moviesPaged.collect {
                    it?.let {
                        it.data?.let { movies ->
                            //TODO may cause bug dd temp solve
                            if (movies.page == 1) {
                                currentMovieList = emptyList()
                                currentPage = 1
                            }
                            val lst = currentMovieList + movies.results as List<Movies.Result?>
                            currentMovieList = lst
                            updateMoviesAdapter(lst)
                            movies.page?.let {
                                movies.totalPages?.let {
                                    if (movies.page<movies.totalPages) {
                                        delay(100)
                                        requestNextPage = true
                                    }
                                }
                            }


                        }
                    }
                }
            }
        } else {
            binding.header.visibility=View.VISIBLE

            viewLifecycleOwner.lifecycleScope.launch {
                moviesViewModel.moviePaging.collect {
                    it?.let {
                        it.data?.let { movies ->
                            //TODO may cause bug dd temp solve
                            if (movies.page == 1) {
                                currentMovieList = emptyList()
                                currentPage = 1
                            }
                            val lst = currentMovieList + movies.results as List<Movies.Result?>
                            currentMovieList = lst
                            updateMoviesAdapter(lst)
                            movies.page?.let {
                                movies.totalPages?.let {
                                    if (movies.page<movies.totalPages) {
                                        delay(100)
                                        requestNextPage = true
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.moviesGenres.collect {
                it?.let {
                    it.data?.let { genre ->
                        genre.genres?.let { updateGenreAdapters(genre.genres) }


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
                            val txt = requireActivity().findViewById<SearchView>(R.id.searchView)
                            searchViewModel.searchMovieByPage(txt.query.toString(), ++currentPage)
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
                    }
                    requestNextPage = false

                }
            }
        })

        moviesAdapter.onItemClick = {
            if (it!=null){
                try {
                    val action =
                        FoundMoviesFragmentDirections.actionFoundMoviesFragmentToMovieDetailsFragment(
                            it
                        )
                    findNavController().navigate(action)
                }catch (e:Exception){
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