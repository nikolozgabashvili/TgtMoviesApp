package com.example.tgtmoviesapp.application.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.presentation.adapters.celebritiesAdapter.CelebritiesAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.movieAdapters.MovieAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters.TvShowsAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.MoviesViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.SearchViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.TvShowsViewModel
import com.example.tgtmoviesapp.databinding.FragmentFoundThingsBinding
import kotlinx.coroutines.launch


class FoundThingsFragment : Fragment() {

    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var movieRecyclerView: RecyclerView

    private lateinit var tvShowsAdapter: TvShowsAdapter
    private lateinit var tvShowsRecyclerView: RecyclerView

    private lateinit var peopleAdapter: CelebritiesAdapter
    private lateinit var peopleRecycler: RecyclerView

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val moviesViewModel: MoviesViewModel by activityViewModels()
    private val tvSearchViewModel: TvShowsViewModel by activityViewModels()


    private var _binding: FragmentFoundThingsBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoundThingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        lifecycleScope.launch {
            searchViewModel.movies.collect {
                it?.let {
                    it.data?.let { movies ->
                        updateMoviesAdapter(movies)


                    }
                }
            }
        }

        lifecycleScope.launch {
            searchViewModel.tvShows.collect {
                it?.let {
                    it.data?.let { movies ->
                        updateTvShowsAdapter(movies)
                    }
                }
            }
        }

        lifecycleScope.launch {
            searchViewModel.people.collect {
                it?.let {
                    it.data?.let { movies ->
                        updatePeopleAdapter(movies)

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

                it?.let { resource ->
                    resource.data?.let { genre ->
                        genre.genres?.let { lst ->

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

    private fun updatePeopleAdapter(movies: Person) {
        movies.results?.let {
            peopleAdapter.setCelebList(it)
        }

    }

    private fun updateTvShowsAdapter(movies: TvShows) {
        tvShowsAdapter.setShowList(movies, DisplayIndicator.WIDE_IMAGE)
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

        tvShowsAdapter = TvShowsAdapter()
        tvShowsRecyclerView = binding.tvShowsRecycler
        tvShowsRecyclerView.adapter = tvShowsAdapter
        tvShowsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        moviesAdapter = MovieAdapter()
        movieRecyclerView = binding.moviesRecycler
        movieRecyclerView.adapter = moviesAdapter
        movieRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }


}