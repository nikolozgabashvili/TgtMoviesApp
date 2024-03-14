package com.example.tgtmoviesapp.application.presentation.fragments.movies

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.application.commons.constants.Constants.PROFILE_IMAGE_URL
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.MovieDetails
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.presentation.adapters.movieAdapters.MovieAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.searchAdapter.SearchAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters.VideoAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.DetailsViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.MoviesViewModel
import com.example.tgtmoviesapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var genreRecyclerView: RecyclerView
    private lateinit var genreAdapter: SearchAdapter

    private lateinit var castRecyclerView: RecyclerView
    private lateinit var similarRecyclerView: RecyclerView
    private lateinit var similarAdapter: MovieAdapter
    private lateinit var recommendedRecyclerView: RecyclerView
    private lateinit var recommendedAdapter: MovieAdapter

    private lateinit var videoRecycler: RecyclerView
    private lateinit var videoAdapter: VideoAdapter

    private val detailsViewModel: DetailsViewModel by activityViewModels()
    private val moviesViewModel: MoviesViewModel by activityViewModels()

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private var args: Int? = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        args = arguments?.getInt("movieId", -1) ?: -1
        setupObservers()
        detailsViewModel.getMovieDetails(args!!)
        detailsViewModel.getSimilarMovies(args!!, 1)
        detailsViewModel.getRecommendedMovies(args!!, 1)
        detailsViewModel.getMovieVideos(args!!)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {

            detailsViewModel.movieDetails.collect {
                it?.let {
                    it.data?.let {

                        setupPage(it, args)
                    }
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {

            detailsViewModel.videos.collect {
                it?.data?.let {
                    it.results?.let {

                        videoAdapter.setShowList(it)
                    }
                }
            }

        }
        viewLifecycleOwner.lifecycleScope.launch {

            detailsViewModel.similarMovies.collect {
                it?.let {
                    it.data?.let {
                        if (it.results?.isEmpty() != false) {
                            similarRecyclerView.visibility = View.GONE
                            binding.similarMovies.visibility = View.GONE
                            binding.seeSimilarMovies.visibility = View.GONE
                        } else {
                            similarRecyclerView.visibility = View.VISIBLE
                            binding.similarMovies.visibility = View.VISIBLE
                            binding.seeSimilarMovies.visibility = View.VISIBLE

                            setupSimilarMoviesRecycler(it)
                        }
                    }
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {

            detailsViewModel.recommended.collect {
                it?.let {
                    it.data?.let {
                        if (it.results?.isEmpty() != false) {
                            recommendedRecyclerView.visibility = View.GONE
                            binding.recommendedMovies.visibility = View.GONE
                            binding.seeRecommended.visibility = View.GONE

                        } else {
                            recommendedRecyclerView.visibility = View.VISIBLE
                            binding.recommendedMovies.visibility = View.VISIBLE
                            binding.seeRecommended.visibility = View.VISIBLE

                            setupRecommendedMoviesRecycler(it)
                        }
                    }
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.moviesGenres.collect {
                it?.data?.let {
                    it.genres?.let {
                        similarAdapter.setMovieGenres(it)
                        recommendedAdapter.setMovieGenres(it)

                    }

                }

            }
        }

    }

    private fun updateGenreList(it: List<Genre?>?) {

    }

    private fun setupRecommendedMoviesRecycler(moviesModel: Movies) {
        moviesModel.results?.let {
            recommendedAdapter.setMovieList(it)
        }
    }

    private fun setupPage(curMovie: MovieDetails, id: Int?) {
        binding.ratingBar.stepSize = 0.2f
        Glide.with(requireContext()).load(PROFILE_IMAGE_URL + curMovie.backdropPath)
            .into(binding.backgroundImage)
        Glide.with(requireContext()).load(PROFILE_IMAGE_URL + curMovie.posterPath)
            .into(binding.posterImage)
        binding.headTitle.maxLines = 1
        binding.movieTitle.text = curMovie.title
        binding.headTitle.text = curMovie.title
        binding.movieAboutInfo.text = curMovie.overview
        binding.collection.visibility = View.GONE
        binding.collectionRecyclerView.visibility = View.GONE
        binding.ratingBar.rating = curMovie.voteAverage?.toFloat()?.div(2) ?: 0f
        binding.avgRating.text = curMovie.voteAverage.toString()
        binding.ratingCount.text = "(${curMovie.voteCount})"

    }



    private fun setupGenresAdapter() {
        genreRecyclerView = binding.genreRecycler
        genreAdapter = SearchAdapter()
        genreRecyclerView.adapter = genreAdapter
        genreRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupSimilarMoviesRecycler(moviesModel: Movies) {

        moviesModel.results?.let {
            similarAdapter.setMovieList(it)
        }


    }

    private fun initAdapters() {
        initRecommendedAdapter()
        initSimilarAdapter()
        setupGenresAdapter()
        setupVideoAdapters()



    }

    private fun setupVideoAdapters() {
        videoAdapter = VideoAdapter()
        videoRecycler = binding.videoRecyclerview
        videoRecycler.adapter = videoAdapter
        videoRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        videoAdapter.onItemClick = {
            it?.let {
                val videoId = it
                val url = "https://www.youtube.com/watch?v=$videoId"
                val packageName = "com.google.android.youtube"
                val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
                val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                try {
                    requireActivity().packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                    startActivity(intentApp)
                } catch (e: Exception) {
                    startActivity(intentWeb)

                }

            }
        }
    }

    private fun initRecommendedAdapter() {
        recommendedAdapter = MovieAdapter()
        recommendedRecyclerView = binding.recommendedRecycler
        recommendedRecyclerView.adapter = recommendedAdapter
        recommendedRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }

    private fun initSimilarAdapter() {
        similarAdapter = MovieAdapter()
        similarRecyclerView = binding.similarRecycler
        similarRecyclerView.adapter = similarAdapter
        similarRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }


}