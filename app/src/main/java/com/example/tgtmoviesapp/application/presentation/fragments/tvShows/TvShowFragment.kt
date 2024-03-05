package com.example.tgtmoviesapp.application.presentation.fragments.tvShows

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters.TopRatedShowsAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters.TvShowsAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.TvShowsViewModel
import com.example.tgtmoviesapp.databinding.FragmentTvShowBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    private var _binding:FragmentTvShowBinding? = null
    private val binding get() = _binding!!
    private val tvShowsViewModel : TvShowsViewModel by activityViewModels()

    private lateinit var trendingAdapter: TvShowsAdapter
    private lateinit var popularAdapter: TvShowsAdapter
    private lateinit var upcomingAdapter: TvShowsAdapter
    private lateinit var topRatedAdapter: TopRatedShowsAdapter

    private lateinit var trendingRecyclerView: RecyclerView
    private lateinit var popularRecyclerView: RecyclerView
    private lateinit var upcomingRecyclerView: RecyclerView
    private lateinit var topRatedRecyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        setupObservers()
        setupListeners()


    }

    private fun setupListeners() {

        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->

            if (scrollY>0){
                binding.header.setBackgroundColor(Color.parseColor("#122727"))
            }else{
                binding.header.setBackgroundColor(Color.parseColor("#0f1c1c"))
            }

        }

        binding.seePopularTvShows.setOnClickListener {
            val action = TvShowFragmentDirections.actionTvShowFragmentToFoundShowsFragment("POPULAR")
            tvShowsViewModel.setMoviePagingValue(tvShowsViewModel.popularTvShows.value)
            findNavController().navigate(action)
        }
        binding.seeTrendingTvShows.setOnClickListener {
            val action = TvShowFragmentDirections.actionTvShowFragmentToFoundShowsFragment("TRENDING")
            tvShowsViewModel.setMoviePagingValue(tvShowsViewModel.trendingTvShows.value)
            findNavController().navigate(action)
        }
        binding.seeTopRatedMovies.setOnClickListener {
            val action = TvShowFragmentDirections.actionTvShowFragmentToFoundShowsFragment("TOP_RATED")
            tvShowsViewModel.setMoviePagingValue(tvShowsViewModel.topRatedTvShows.value)
            findNavController().navigate(action)
        }
        binding.seeAiringTodayTvShows.setOnClickListener {
            val action = TvShowFragmentDirections.actionTvShowFragmentToFoundShowsFragment("UPCOMING")
            tvShowsViewModel.setMoviePagingValue(tvShowsViewModel.upcomingTvShows.value)
            findNavController().navigate(action)

        }
    }

    private fun setupObservers() {

        lifecycleScope.launch {
            tvShowsViewModel.tvGenres.collect {
                it?.let {resource->
                    resource.data?.let {genre->
                        genre.genres?.let {lst->

                            updateAdapters(lst)

                        }
                    }
                }

            }
        }

        lifecycleScope.launch {
            tvShowsViewModel.popularTvShows.collect {
                it?.let {
                    it.data?.let { data ->
                        updatePopularAdapter(data)
                    }

                }
            }
        }

        lifecycleScope.launch {
            tvShowsViewModel.trendingTvShows.collect {
                it?.let {
                    it.data?.let { data ->
                        updateTrendingAdapter(data)
                    }

                }
            }
        }








        lifecycleScope.launch {
            tvShowsViewModel.topRatedTvShows.collect {
                it?.let {
                    it.data?.let {
                        updateTopRatedAdapter(it)
                    }
                }

            }
        }

        lifecycleScope.launch {
            tvShowsViewModel.upcomingTvShows.collect {
                it?.let {
                    it.data?.let {
                        updateUpcomingAdapter(it)
                    }
                }

            }
        }
    }

    private fun updateAdapters(data: List<TvGenre.Genre?>) {
        trendingAdapter.setMovieGenres(data)
        topRatedAdapter.setMovieGenres(data)
        popularAdapter.setMovieGenres(data)
        upcomingAdapter.setMovieGenres(data)

    }

    private fun updateTrendingAdapter(data: TvShows) {
        trendingAdapter.setShowList(data)
    }

    private fun updatePopularAdapter(data: TvShows) {
        popularAdapter.setShowList(data)

    }

    private fun updateUpcomingAdapter(data: TvShows) {
        upcomingAdapter.setShowList(data,DisplayIndicator.WIDE_IMAGE)

    }

    private fun updateTopRatedAdapter(data: TvShows) {
        data.results?.let { topRatedAdapter.setShowList(data.results) }


    }

    private fun initAdapters() {
        initPopularAdapter()
        initUpcomingAdapter()
        initTopRatedAdapter()
        initTrendingAdapter()


    }

    private fun initTopRatedAdapter() {
        topRatedAdapter = TopRatedShowsAdapter()
        topRatedRecyclerView = binding.topRatedTvShowsRecyclerGrid
        topRatedRecyclerView.adapter = topRatedAdapter
        topRatedRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 4, GridLayoutManager.HORIZONTAL, false)
    }

    private fun initUpcomingAdapter() {
        upcomingAdapter = TvShowsAdapter()
        upcomingRecyclerView = binding.todayAiringTvShowsRecycler
        upcomingRecyclerView.adapter = upcomingAdapter
        upcomingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }

    private fun initPopularAdapter() {
        popularAdapter = TvShowsAdapter()
        popularRecyclerView = binding.popularTvShowsRecycler
        popularRecyclerView.adapter = popularAdapter
        popularRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initTrendingAdapter() {
        trendingAdapter = TvShowsAdapter()
        trendingRecyclerView = binding.trendingTvShowsRecycler
        trendingRecyclerView.adapter = trendingAdapter
        trendingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}