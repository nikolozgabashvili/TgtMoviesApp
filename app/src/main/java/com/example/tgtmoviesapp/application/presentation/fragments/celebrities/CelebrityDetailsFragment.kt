package com.example.tgtmoviesapp.application.presentation.fragments.celebrities

import android.media.tv.TvRecordingClient
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.constants.Constants.PROFILE_IMAGE_URL
import com.example.tgtmoviesapp.application.commons.ext.toDate
import com.example.tgtmoviesapp.application.domain.models.PersonDetails
import com.example.tgtmoviesapp.application.presentation.adapters.movieAdapters.MovieAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters.TvShowsAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.CelebrityDetailsViewModel
import com.example.tgtmoviesapp.databinding.FragmentCelebrityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CelebrityDetailsFragment : Fragment() {

    private var _binding:FragmentCelebrityDetailsBinding? = null
    private val binding get()= _binding!!
    private var args :Int = -1

    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    private lateinit var tvRecyclerView: RecyclerView
    private lateinit var tvAdapter: TvShowsAdapter


    private val detailsViewModel:CelebrityDetailsViewModel by viewModels ()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentCelebrityDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        args = arguments?.getInt("id", -1) ?: -1
        setupObservers()


        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.seemovies.setOnClickListener {
            val action = CelebrityDetailsFragmentDirections.actionCelebrityDetailsFragmentToFoundMoviesFragment("Movies",args)
            findNavController().navigate(action)
        }

        binding.seeTvShows.setOnClickListener {
            val action = CelebrityDetailsFragmentDirections.actionCelebrityDetailsFragmentToFoundShowsFragment("Shows",args)
            findNavController().navigate(action)
        }

    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.detailsScroll.visibility = View.INVISIBLE
            detailsViewModel.peopleDetails.collect {

                if (it==null)
                    detailsViewModel.getPersonDetails(args)

                it?.let {
                    it.data?.let {
                        setupPage(it, args)

                    }
                }
                if (it?.loading==null){
                    binding.detailsScroll.visibility = View.VISIBLE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            detailsViewModel.movies.collect{

                if (it==null)
                    detailsViewModel.getMovies(args)

                it?.let {
                    it.data?.let {
                        if (!it.results.isNullOrEmpty()){
                            if (it.results.size>20){
                                movieAdapter.setMovieList(it.results.subList(0,20))
                            }else {
                                movieAdapter.setMovieList(it.results)
                            }
                            binding.moviesRecycler.visibility = View.VISIBLE
                            binding.movies.visibility = View.VISIBLE
                            binding.seemovies.visibility = View.VISIBLE
                        }else{
                            binding.moviesRecycler.visibility = View.GONE
                            binding.movies.visibility = View.GONE
                            binding.seemovies.visibility = View.GONE
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            detailsViewModel.tvShows.collect{

                if (it==null)
                    detailsViewModel.getTvShows(args)

                it?.let {
                    it.data?.let {
                        if (!it.results.isNullOrEmpty()){
                            if (it.results.size>20)
                                tvAdapter.setShowList(it.results.subList(0,20))
                            else
                                tvAdapter.setShowList(it.results)
                            binding.tvShowsRecycler.visibility = View.VISIBLE
                            binding.tvShows.visibility = View.VISIBLE
                            binding.seeTvShows.visibility = View.VISIBLE
                        }else{
                            binding.tvShowsRecycler.visibility = View.GONE
                            binding.tvShows.visibility = View.GONE
                            binding.seeTvShows.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun setupPage(person: PersonDetails, args: Int) {

        Glide.with(requireContext())
            .load(PROFILE_IMAGE_URL+person.profilePath)
            .placeholder(R.drawable.person_item)
            .override(500,700)
            .centerCrop()
            .transform(RoundedCorners(30))
            .into(binding.posterImage)

        binding.headTitle.text = person.name
        binding.celebrityName.text = person.name

        if (!person.knownForDepartment.isNullOrEmpty()){
            binding.knownForTxt.text = person.knownForDepartment
        }else
        {
            binding.knownFor.visibility = View.GONE
            binding.knownForTxt.visibility = View.GONE
        }

        if (!person.birthday.isNullOrEmpty()){
            binding.dateOfBirthTxt.text = person.birthday.toDate()
        }else{
            binding.dateOfBirth.visibility = View.GONE
            binding.dateOfBirthTxt.visibility = View.GONE
        }
        if (!person.deathDay.isNullOrEmpty()){
            binding.deathDayTxt.text = person.deathDay.toDate()
        }else{
            binding.deathDay.visibility = View.GONE
            binding.deathDayTxt.visibility = View.GONE
        }

        if (!person.placeOfBirth.isNullOrEmpty()){
            binding.birthplaceTxt.text = person.placeOfBirth
        }else{
            binding.birthplace.visibility = View.GONE
            binding.birthplaceTxt.visibility = View.GONE
        }
        if (!person.biography.isNullOrEmpty()){
            binding.biography.text = person.biography
        }else{
            binding.biography.visibility = View.GONE
        }

    }

    private fun initAdapters() {
        movieRecyclerView = binding.moviesRecycler
        movieAdapter = MovieAdapter()
        movieRecyclerView.adapter = movieAdapter
        movieRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        movieAdapter.onItemClick = {
            it?.let {
                val action = CelebrityDetailsFragmentDirections.actionCelebrityDetailsFragmentToMovieDetailsFragment(it)
                findNavController().navigate(action)
            }
        }

        tvRecyclerView = binding.tvShowsRecycler
        tvAdapter = TvShowsAdapter()
        tvRecyclerView.adapter = tvAdapter
        tvRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        tvAdapter.onItemClick = {
            it?.let {
                val action = CelebrityDetailsFragmentDirections.actionCelebrityDetailsFragmentToTvDetailsFragment(it)
                findNavController().navigate(action)
            }
        }

    }


}