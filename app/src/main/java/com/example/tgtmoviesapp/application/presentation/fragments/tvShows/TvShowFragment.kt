package com.example.tgtmoviesapp.application.presentation.fragments.tvShows

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
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters.TopRatedShowsAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters.TvShowsAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.TvShowsViewModel
import com.example.tgtmoviesapp.databinding.FragmentTvShowBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!
    private val tvShowsViewModel: TvShowsViewModel by activityViewModels()

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
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
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

            if (scrollY > 0) {
                binding.header.setBackgroundColor(Color.parseColor("#122727"))
            } else {
                binding.header.setBackgroundColor(Color.parseColor("#0f1c1c"))
            }

        }

        binding.seePopularTvShows.setOnClickListener {
            val action =
                TvShowFragmentDirections.actionTvShowFragmentToFoundShowsFragment("Popular")
            tvShowsViewModel.setMoviePagingValue(tvShowsViewModel.popularTvShows.value)
            findNavController().navigate(action)
        }
        binding.seeTrendingTvShows.setOnClickListener {
            val action =
                TvShowFragmentDirections.actionTvShowFragmentToFoundShowsFragment("Trending")
            tvShowsViewModel.setMoviePagingValue(tvShowsViewModel.trendingTvShows.value)
            findNavController().navigate(action)
        }
        binding.seeTopRatedMovies.setOnClickListener {
            val action =
                TvShowFragmentDirections.actionTvShowFragmentToFoundShowsFragment("Top Rated")
            tvShowsViewModel.setMoviePagingValue(tvShowsViewModel.topRatedTvShows.value)
            findNavController().navigate(action)
        }
        binding.seeAiringTodayTvShows.setOnClickListener {
            val action =
                TvShowFragmentDirections.actionTvShowFragmentToFoundShowsFragment("Airing Today")
            tvShowsViewModel.setMoviePagingValue(tvShowsViewModel.upcomingTvShows.value)
            findNavController().navigate(action)

        }
    }

    private fun setupObservers() {


        viewLifecycleOwner.lifecycleScope.launch {
            tvShowsViewModel.popularTvShows.collect {
                it?.let {
                    it.data?.let { data ->
                        updatePopularAdapter(data)
                    }

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            tvShowsViewModel.trendingTvShows.collect {
                it?.let {
                    it.data?.let { data ->
                        updateTrendingAdapter(data)
                    }

                }
            }
        }








        viewLifecycleOwner.lifecycleScope.launch {
            tvShowsViewModel.topRatedTvShows.collect {
                it?.let {
                    it.data?.let {
                        updateTopRatedAdapter(it)
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            tvShowsViewModel.upcomingTvShows.collect {
                it?.let {
                    it.data?.let {
                        updateUpcomingAdapter(it)
                    }
                }

            }
        }
    }



    private fun updateTrendingAdapter(data: TvShows) {
        data.results?.let {

            trendingAdapter.setShowList(it)
        }
    }

    private fun updatePopularAdapter(data: TvShows) {
        data.results?.let {
            popularAdapter.setShowList(it)
        }

    }

    private fun updateUpcomingAdapter(data: TvShows) {
        data.results?.let {

            upcomingAdapter.setShowList(it, DisplayIndicator.WIDE_IMAGE)
        }

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

        topRatedAdapter.onItemClick = {
            val action = TvShowFragmentDirections.actionTvShowFragmentToTvDetailsFragment(it ?: -1)
            findNavController().navigate(action)
        }
    }

    private fun initUpcomingAdapter() {
        upcomingAdapter = TvShowsAdapter()
        upcomingRecyclerView = binding.todayAiringTvShowsRecycler
        upcomingRecyclerView.adapter = upcomingAdapter
        upcomingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        upcomingAdapter.onItemClick = {
            val action = TvShowFragmentDirections.actionTvShowFragmentToTvDetailsFragment(it ?: -1)
            findNavController().navigate(action)
        }
    }

    private fun initPopularAdapter() {
        popularAdapter = TvShowsAdapter()
        popularRecyclerView = binding.popularTvShowsRecycler
        popularRecyclerView.adapter = popularAdapter
        popularRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularAdapter.onItemClick = {
            val action = TvShowFragmentDirections.actionTvShowFragmentToTvDetailsFragment(it ?: -1)
            findNavController().navigate(action)
        }
    }

    private fun initTrendingAdapter() {
        trendingAdapter = TvShowsAdapter()
        trendingRecyclerView = binding.trendingTvShowsRecycler
        trendingRecyclerView.adapter = trendingAdapter
        trendingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        trendingAdapter.onItemClick = {
            val action = TvShowFragmentDirections.actionTvShowFragmentToTvDetailsFragment(it ?: -1)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}