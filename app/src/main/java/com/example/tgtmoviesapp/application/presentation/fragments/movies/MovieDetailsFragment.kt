package com.example.tgtmoviesapp.application.presentation.fragments.movies

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.constants.Constants.PROFILE_IMAGE_URL
import com.example.tgtmoviesapp.application.commons.ext.convert
import com.example.tgtmoviesapp.application.commons.ext.toCompanyList
import com.example.tgtmoviesapp.application.commons.ext.toDate
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Genre
import com.example.tgtmoviesapp.application.domain.models.MovieDetails
import com.example.tgtmoviesapp.application.domain.models.Movies
import com.example.tgtmoviesapp.application.presentation.adapters.celebritiesAdapter.CelebritiesAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.movieAdapters.MovieAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.searchAdapter.SearchAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters.VideoAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.DetailsViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.MoviesViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.UserViewModel
import com.example.tgtmoviesapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var genreRecyclerView: RecyclerView
    private lateinit var genreAdapter: SearchAdapter

    private lateinit var castRecyclerView: RecyclerView
    private lateinit var castAdapter: CelebritiesAdapter
    private lateinit var similarRecyclerView: RecyclerView
    private lateinit var similarAdapter: MovieAdapter
    private lateinit var recommendedRecyclerView: RecyclerView
    private lateinit var recommendedAdapter: MovieAdapter

    private lateinit var videoRecycler: RecyclerView
    private lateinit var videoAdapter: VideoAdapter

    private val detailsViewModel: DetailsViewModel by viewModels()
    private val moviesViewModel: MoviesViewModel by viewModels()

    private val userViewModel: UserViewModel by viewModels()

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
        binding.seeRecommended.setOnClickListener {
            val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToFoundMoviesFragment("Recommended",args!!)
            findNavController().navigate(action)
        }
        binding.seeSimilarMovies.setOnClickListener {
            val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToFoundMoviesFragment("Similar",args!!)
            findNavController().navigate(action)
        }



        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.favourites.setOnClickListener {
            if (userViewModel.currentUserToken.value == null) {
                Toast.makeText(requireContext(), "please log in", Toast.LENGTH_SHORT).show()
            } else if (userViewModel.isFavourite.value?.data == true) {

                userViewModel.deleteFav(args!!)

                viewLifecycleOwner.lifecycleScope.launch {

                    userViewModel.deleteSuccess.collect {
                        if (it?.data == true) {
                            binding.favourites.setImageResource(R.drawable.favourite_empty)
                        } else if (it?.data == false) {
                            if (!it.message.isNullOrEmpty() && it.message[0] == "session ended") {
                                Toast.makeText(
                                    requireContext(),
                                    "session ended please sign in",
                                    Toast.LENGTH_SHORT
                                ).show()
                                userViewModel.removeTokenValue()
                            }
                        }
                    }
                }
            } else if (userViewModel.isFavourite.value?.data == false) {
                userViewModel.addFavourite(args!!)
                viewLifecycleOwner.lifecycleScope.launch {
                    userViewModel.favouriteSuccess.collect {
                        if (it) {
                            binding.favourites.setImageResource(R.drawable.favourite_filled)

                        }
                    }
                }
            }
        }


    }

    private fun setupObservers() {


        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.isFavourite.collect {
                if  (it==null){
                    userViewModel.isMovieFavourite(args!!)
                }
                if (it?.message != null && it.message.isNotEmpty() && it.message[0] == "session ended") {
                    Toast.makeText(
                        requireContext(),
                        "Session ended please log in ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {


                    if (it?.data == true) {
                        binding.favourites.setImageResource(R.drawable.favourite_filled)
                    } else {
                        binding.favourites.setImageResource(R.drawable.favourite_empty)

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            binding.detailsScroll.visibility = View.INVISIBLE
            detailsViewModel.movieDetails.collect {

                if (it==null)
                    detailsViewModel.getMovieDetails(args!!)

                it?.let {
                    it.data?.let {

                        setupPage(it, args)
                        updateGenreList(it.genres)
                    }
                }
                if (it?.loading==null){
                    binding.detailsScroll.visibility = View.VISIBLE
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {

            detailsViewModel.videos.collect {
                if (it==null){
                    detailsViewModel.getMovieVideos(args!!)
                }
                it?.data?.let {
                    it.results?.let {
                        if (it.isNotEmpty()) {
                            binding.videos.visibility = View.VISIBLE
                            binding.videoRecyclerview.visibility = View.VISIBLE
                            videoAdapter.setShowList(it)
                        } else {
                            binding.videos.visibility = View.GONE
                            binding.videoRecyclerview.visibility = View.GONE

                        }
                    }
                }
            }

        }
        viewLifecycleOwner.lifecycleScope.launch {

            detailsViewModel.similarMovies.collect {
                if (it==null)
                    detailsViewModel.getSimilarMovies(args!!, 1)

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
                if (it==null)
                    detailsViewModel.getRecommendedMovies(args!!, 1)
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


                    }

                }

            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            detailsViewModel.cast.collect {
                if (it==null){
                    detailsViewModel.getCast(args!!)
                }
                it?.data?.let {
                    it.results?.let {
                        if (it.isNotEmpty()) {
                            binding.cast.visibility = View.VISIBLE
                            binding.castRecycler.visibility = View.VISIBLE
                            castAdapter.setCelebList(it, DisplayIndicator.DETAILS)
                        } else {
                            binding.cast.visibility = View.GONE
                            binding.castRecycler.visibility = View.GONE
                        }

                    }

                }

            }
        }

    }

    private fun updateGenreList(it: List<Genre?>?) {
        val tempList = mutableListOf<String>()
        it?.map {
            it?.name?.let {
                tempList.add(it)
            }
        }
        genreAdapter.setMovieList(tempList, DisplayIndicator.DETAILS)
    }

    private fun setupRecommendedMoviesRecycler(moviesModel: Movies) {
        moviesModel.results?.let {
            recommendedAdapter.setMovieList(it)
        }
    }

    private fun setupPage(curMovie: MovieDetails, id: Int?) {
        binding.ratingBar.stepSize = 0.2f

        Glide.with(requireContext()).load(PROFILE_IMAGE_URL + curMovie.backdropPath)
            .placeholder(R.drawable.movies_item)

            .into(binding.backgroundImage)
        Glide.with(requireContext()).load(PROFILE_IMAGE_URL + curMovie.posterPath)
            .placeholder(R.drawable.movies_item)

            .centerCrop()
            .into(binding.posterImage)
        binding.headTitle.maxLines = 1
        binding.movieTitle.text = curMovie.title
        binding.headTitle.text = curMovie.title
        binding.movieAboutInfo.text = curMovie.overview
        binding.collection.visibility = View.GONE
        binding.collectionRecyclerView.visibility = View.GONE
        binding.ratingBar.rating = curMovie.voteAverage?.toFloat()?.div(2) ?: 0f

        curMovie.voteAverage?.let {
            binding.avgRating.text = "${(it)}"
        }
        binding.ratingCount.text = "(${curMovie.voteCount})"

        binding.releaseDateTxt.text = curMovie.releaseDate.toDate()
        binding.languageTxt.text = curMovie.originalLanguage
        if (curMovie.budget == null || curMovie.budget == 0) {
            binding.budgetTxt.visibility = View.GONE
            binding.budget.visibility = View.GONE
        } else {
            binding.budgetTxt.visibility = View.VISIBLE
            binding.budget.visibility = View.VISIBLE
            binding.budgetTxt.text = curMovie.budget.convert()

        }
        if (curMovie.revenue == null || curMovie.revenue == 0) {
            binding.revenueTxt.visibility = View.GONE
            binding.revenue.visibility = View.GONE
        } else {
            binding.revenueTxt.visibility = View.VISIBLE
            binding.revenue.visibility = View.VISIBLE
            binding.revenueTxt.text = curMovie.revenue.convert()
        }
        curMovie.productionCompanies?.let {
            if (curMovie.productionCompanies.isNotEmpty()) {
            binding.productionCompTxt.visibility = View.VISIBLE
            binding.productionComp.visibility = View.VISIBLE
            binding.productionCompTxt.text = curMovie.productionCompanies.toCompanyList()
        } else {
            binding.productionCompTxt.visibility = View.GONE
            binding.productionComp.visibility = View.GONE
        } }


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
        initCastAdapter()


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
                    requireActivity().packageManager.getPackageInfo(
                        packageName,
                        PackageManager.GET_ACTIVITIES
                    )
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

        recommendedAdapter.onItemClick = {
            it?.let {
                val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(it)
                findNavController().navigate(action)
            }
        }

    }

    private fun initSimilarAdapter() {
        similarAdapter = MovieAdapter()
        similarRecyclerView = binding.similarRecycler
        similarRecyclerView.adapter = similarAdapter
        similarRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        similarAdapter.onItemClick = {
            it?.let {
                val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(it)
                findNavController().navigate(action)
            }
        }
    }

    private fun initCastAdapter() {
        castAdapter = CelebritiesAdapter()
        castRecyclerView = binding.castRecycler
        castRecyclerView.adapter = castAdapter
        castRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        castAdapter.onClick={
            it?.let {
                val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToCelebrityDetailsFragment(it)
                findNavController().navigate(action)
            }
        }

    }


}