package com.example.tgtmoviesapp.application.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.celebritiesAdapter.CelebritiesAdapter
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.movieAdapters.MovieAdapter
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.searchAdapter.SearchAdapter
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.tvshowAdapters.TopRatedShowsAdapter
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.tvshowAdapters.TvShowsAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.MoviesViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.SearchViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.TvShowsViewModel
import com.example.tgtmoviesapp.databinding.FragmentSecondSearchBinding
import kotlinx.coroutines.launch


class SecondSearchFragment : Fragment() {
    private lateinit var searchView: android.widget.SearchView
    private var _binding: FragmentSecondSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchAdapter: SearchAdapter
    private lateinit var miniSearchRecyclerView: RecyclerView

    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var movieRecyclerView: RecyclerView

    private lateinit var tvShowsAdapter: TvShowsAdapter
    private lateinit var tvShowsRecyclerView: RecyclerView

    private lateinit var peopleAdapter: CelebritiesAdapter
    private lateinit var peopleRecycler: RecyclerView

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val moviesViewModel: MoviesViewModel by activityViewModels()
    private val tvSearchViewModel: TvShowsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = binding.searchView
        initAdapters()
        setupObservers()
        search()


        binding.backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }



    }

    fun setDefaultViews(){
        binding.movieDisplay.visibility = View.INVISIBLE
        binding.tabLayout.visibility = View.INVISIBLE
        binding.predictiveSearchDisplay.visibility = View.VISIBLE

    }


    private fun updateSearchAdapter(data: List<String>) {
        searchAdapter.setMovieList(data)
    }

    private fun initAdapters() {
        searchAdapter = SearchAdapter()
        miniSearchRecyclerView = binding.predictiveSearchDisplay
        miniSearchRecyclerView.adapter = searchAdapter
        miniSearchRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        peopleAdapter = CelebritiesAdapter()
        peopleRecycler = binding.peopleRecycler
        peopleRecycler.adapter = peopleAdapter
        peopleRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        tvShowsAdapter = TvShowsAdapter()
        tvShowsRecyclerView = binding.tvShowsRecycler
        tvShowsRecyclerView.adapter = tvShowsAdapter
        tvShowsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        moviesAdapter = MovieAdapter()
        movieRecyclerView = binding.movieRecycler
        movieRecyclerView.adapter = moviesAdapter
        movieRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


    }


    private fun setupObservers() {

        lifecycleScope.launch {
            searchViewModel.searchNameList.collect {

                if (it.isNotEmpty()) {

                    updateSearchAdapter(it)
                    miniSearchRecyclerView.visibility = View.VISIBLE
                    binding.blankTextView.visibility = View.INVISIBLE

                } else {
                    miniSearchRecyclerView.visibility = View.INVISIBLE
                    binding.blankTextView.visibility = View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
            searchViewModel.movies.collect {
                it?.let {
                    it.data?.let { movies ->
                        updateMoviesAdapter(movies)
                        //temporary fix ->>
                        setupFundInfoDisplay()

                    }
                }
            }
        }

        lifecycleScope.launch {
            searchViewModel.tvShows.collect {
                it?.let {
                    it.data?.let { movies ->
                        updateTvShowsAdapter(movies)
                        setupFundInfoDisplay()
                    }
                }
            }
        }

        lifecycleScope.launch {
            searchViewModel.people.collect {
                it?.let {
                    it.data?.let { movies ->
                        updatePeopleAdapter(movies)
                        setupFundInfoDisplay()
                    }
                }
            }
        }
        lifecycleScope.launch {
            moviesViewModel.moviesGenres.collect {

                it?.let {resource->
                    resource.data?.let {genre->
                        genre.genres?.let {lst->

                            updateGenreAdapters(lst)
                        }
                    }
                }


            }

        }
        lifecycleScope.launch {
            tvSearchViewModel.tvGenres.collect {

                it?.let {resource->
                    resource.data?.let {genre->
                        genre.genres?.let {lst->

                            updateTvGenre(lst)
                        }
                    }
                }


            }

        }


    }

    private fun updateTvGenre(lst: List<TvGenre.Genre?>) {
        tvShowsAdapter.setMovieGenres(lst)
    }

    private fun updateGenreAdapters(lst: List<Genre.Genre?>) {
        moviesAdapter.setMovieGenres(lst)
    }

    private fun setupFundInfoDisplay() {
        binding.movieDisplay.visibility = View.VISIBLE
        binding.tabLayout.visibility = View.VISIBLE
        binding.predictiveSearchDisplay.visibility = View.INVISIBLE
        binding.blankTextView.visibility = View.INVISIBLE
    }

    private fun updatePeopleAdapter(movies: Person) {
        movies.results?.let {
            peopleAdapter.setCelebList(it)
        }

    }

    private fun updateTvShowsAdapter(movies: TvShows) {
        tvShowsAdapter.setShowList(movies,DisplayIndicator.WIDE_IMAGE)
    }

    private fun updateMoviesAdapter(movies: Movies) {
        moviesAdapter.setMovieList(movies)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchViewModel.clearSearch()
        _binding = null
    }


    private fun search() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val q = query ?: ""
                searchViewModel.getEverythingByQuery(q)
                setDefaultViews()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val txt = newText ?: ""
                searchViewModel.cancelDebounceJob()
                searchViewModel.startDebounceJob()
                searchViewModel.setTextFlow(txt)
                return true

            }

        }
        )

    }

}