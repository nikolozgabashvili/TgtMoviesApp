package com.example.tgtmoviesapp.application.presentation.fragments.tvShows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters.TopRatedShowsAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.SearchViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.TvShowsViewModel
import com.example.tgtmoviesapp.databinding.FragmentFoundShowsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale


class FoundShowsFragment : Fragment() {


    private var _binding: FragmentFoundShowsBinding? = null
    private val binding get()= _binding!!

    private lateinit var tvShowsAdapter: TopRatedShowsAdapter
    private lateinit var tvShowsRecyclerView: RecyclerView

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val tvSearchViewModel: TvShowsViewModel by activityViewModels()

    private var movieType: String? = "NONE"

    private var currentPage = 1
    private var currentMovieList :List<TvShows.Result?>  = mutableListOf()
    private var requestNextPage = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoundShowsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
        if (movieType=="NONE") {
            binding.header.visibility = View.GONE
            lifecycleScope.launch {
                searchViewModel.tvShowsPaged.collect {
                    it?.let {
                        it.data?.let { movies ->
                            //TODO may cause bug dd temp solve
                            if (movies.page == 1)
                                currentMovieList = emptyList()

                            val lst = currentMovieList + movies.results as List<TvShows.Result?>
                            currentMovieList = lst
                            updateTvAdapter(lst)
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
        }else{
            binding.header.visibility = View.VISIBLE
            lifecycleScope.launch {
                tvSearchViewModel.tvPaging.collect {
                    it?.let {
                        it.data?.let { movies ->
                            //TODO may cause bug dd temp solve
                            if (movies.page == 1)
                                currentMovieList = emptyList()

                            val lst = currentMovieList + movies.results as List<TvShows.Result?>
                            currentMovieList = lst
                            updateTvAdapter(lst)
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
        lifecycleScope.launch {
            tvSearchViewModel.tvGenres.collect{
                it?.let {
                    it.data?.let {  genre->
                        genre.genres?.let {updateGenreAdapters(genre.genres)  }



                    }
                }
            }
        }
    }

    private fun initAdapter(){
        tvShowsAdapter = TopRatedShowsAdapter()
        tvShowsRecyclerView = binding.recycler
        tvShowsRecyclerView.adapter = tvShowsAdapter
        tvShowsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        tvShowsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)



                val layoutManager = recyclerView.layoutManager
                val visibleItemCount = layoutManager?.childCount ?: 0
                val totalItemCount = layoutManager?.itemCount ?: 0
                val firstVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 &&requestNextPage) {
                    when (movieType) {
                        "NONE" -> {
                            val txt = requireActivity().findViewById<SearchView>(R.id.searchView)
                            searchViewModel.searchTvShowsByPage(txt.query.toString(), ++currentPage)
                            println("page:$currentPage")
                        }

                        "Popular" -> {

                            tvSearchViewModel.getPopularTvByPage(++currentPage)
                        }

                        "Trending" -> {
                            tvSearchViewModel.getTrendingTvByPage(++currentPage)
                        }

                        "Top Rated" -> {
                            tvSearchViewModel.getTopRatedTvByPage(++currentPage)
                        }

                        "Airing Today" -> {
                            tvSearchViewModel.getUpcomingTvByPage(++currentPage)
                        }

                    }
                    requestNextPage = false


                }
            }
        })



    }

    private fun updateTvAdapter(movies: List<TvShows.Result?>?) {
        movies?.let {
            tvShowsAdapter.setShowList(movies, DisplayIndicator.FOUND_IMAGE_TYPE)
        }

    }

    private fun updateGenreAdapters(lst: List<TvGenre.Genre?>) {
        tvShowsAdapter.setMovieGenres(lst)
    }



}