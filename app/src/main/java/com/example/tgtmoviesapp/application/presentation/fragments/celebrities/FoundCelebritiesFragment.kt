package com.example.tgtmoviesapp.application.presentation.fragments.celebrities

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
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.presentation.adapters.celebritiesAdapter.TopRatedPeopleAdapter
import com.example.tgtmoviesapp.application.presentation.fragments.search.SecondSearchFragmentDirections
import com.example.tgtmoviesapp.application.presentation.viewModels.CelebritiesViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.SearchViewModel
import com.example.tgtmoviesapp.databinding.FragmentFoundCelebritiesBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale


class FoundCelebritiesFragment : Fragment() {


    private var _binding: FragmentFoundCelebritiesBinding? = null
    private val binding get() = _binding!!

    private lateinit var peopleAdapter: TopRatedPeopleAdapter
    private lateinit var peopleRecyclerView: RecyclerView

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val celebritiesViewModel: CelebritiesViewModel by activityViewModels()

    private var dataType: String? = "NONE"

    private var currentPage = 1
    private var currentMovieList: List<Person.Result?> = mutableListOf()
    private var requestNextPage = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoundCelebritiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataType = arguments?.getString("dataType", "NONE") ?: "NONE"
        binding.movieType.text = dataType?.lowercase().toString().replaceFirstChar { it.titlecase(
            Locale.getDefault()) }
        binding.backButton.setOnClickListener{findNavController().popBackStack()}
        initAdapter()
        setupObserver()

    }

    private fun setupObserver() {
        if (dataType == "NONE") {
            binding.header.visibility = View.GONE
            viewLifecycleOwner.lifecycleScope.launch {
                searchViewModel.peoplePaged.collect {
                    it?.let {
                        it.data?.let { movies ->

                            if (movies.page == 1)
                                currentMovieList = emptyList()

                            val lst = currentMovieList + movies.results as List<Person.Result?>
                            currentMovieList = lst
                            updatePeopleAdapter(lst)
                            movies.page?.let {
                                movies.totalPages?.let {
                                    if (movies.page < movies.totalPages) {
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
            viewLifecycleOwner.lifecycleScope.launch {
                binding.header.visibility = View.VISIBLE
                celebritiesViewModel.peoplePaging.collect {
                    it?.let {
                        it.data?.let { movies ->

                            if (movies.page == 1)
                                currentMovieList = emptyList()

                            val lst = currentMovieList + movies.results as List<Person.Result?>
                            currentMovieList = lst
                            updatePeopleAdapter(lst)
                            movies.page?.let {
                                movies.totalPages?.let {
                                    if (movies.page < movies.totalPages) {
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

    }

    private fun initAdapter() {

        peopleRecyclerView = binding.recycler
        peopleAdapter = TopRatedPeopleAdapter()
        peopleRecyclerView.adapter = peopleAdapter
        peopleRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        peopleAdapter.onClick={
            try {
                it?.let {
                    val action = FoundCelebritiesFragmentDirections.actionFoundCelebritiesFragmentToCelebrityDetailsFragment(it)
                    findNavController().navigate(action)
                }
            }catch (_:Exception){
                it?.let {
                    val action = SecondSearchFragmentDirections.actionSecondSearchFragmentToCelebrityDetailsFragment(it)
                    findNavController().navigate(action)
                }
            }
        }
        peopleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                val layoutManager = recyclerView.layoutManager
                val visibleItemCount = layoutManager?.childCount ?: 0
                val totalItemCount = layoutManager?.itemCount ?: 0
                val firstVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && requestNextPage) {
                    when (dataType) {
                        "NONE" -> {
                            val txt = requireActivity().findViewById<SearchView>(R.id.searchView)
                            searchViewModel.searchPeopleByPage(txt.query.toString(), ++currentPage)
                        }

                        "Popular" -> {

                            celebritiesViewModel.getPopularCelebrityByPage(++currentPage)
                        }

                        "Trending" -> {
                            celebritiesViewModel.getTrendingCelebrityByPage(++currentPage)
                        }
                    }
                    requestNextPage = false

                }
            }
        })


    }

    private fun updatePeopleAdapter(movies: List<Person.Result?>?) {
        movies?.let {

            peopleAdapter.setPersonList(movies, DisplayIndicator.FOUND_IMAGE_TYPE)
        }

    }


}