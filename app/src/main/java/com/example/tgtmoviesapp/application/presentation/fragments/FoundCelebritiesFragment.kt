package com.example.tgtmoviesapp.application.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.domain.models.TvGenre
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.presentation.adapters.celebritiesAdapter.TopRatedPeopleAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.tvshowAdapters.TopRatedShowsAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.CelebritiesViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.SearchViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.TvShowsViewModel
import com.example.tgtmoviesapp.databinding.FragmentFoundCelebritiesBinding
import com.example.tgtmoviesapp.databinding.FragmentFoundThingsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FoundCelebritiesFragment : Fragment() {



    private var _binding: FragmentFoundCelebritiesBinding? = null
    private val binding get()= _binding!!

    private lateinit var peopleAdapter: TopRatedPeopleAdapter
    private lateinit var peopleRecyclerView: RecyclerView

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val peopleSearchViewModel: CelebritiesViewModel by activityViewModels()



    private var currentPage = 1
    private var currentMovieList :List<Person.Result?>  = mutableListOf()
    private var requestNextPage = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoundCelebritiesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setupObserver()

    }

    private fun setupObserver() {
        lifecycleScope.launch {
            searchViewModel.peoplePaged.collect{
                it?.let {
                    it.data?.let { movies ->
                        //TODO may cause bug dd temp solve
                        if (movies.page==1)
                            currentMovieList = emptyList()

                        val lst = currentMovieList+movies.results as List<Person.Result?>
                        currentMovieList = lst
                        updatePeopleAdapter(lst)
                        delay(100)
                        requestNextPage = true

                    }
                }
            }
        }

    }

    private fun initAdapter(){
        peopleAdapter = TopRatedPeopleAdapter()
        peopleRecyclerView = binding.root
        peopleRecyclerView.adapter = peopleAdapter
        peopleRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        peopleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)



                val layoutManager = recyclerView.layoutManager
                val visibleItemCount = layoutManager?.childCount ?: 0
                val totalItemCount = layoutManager?.itemCount ?: 0
                val firstVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 &&requestNextPage) {
                    val txt = requireActivity().findViewById<SearchView>(R.id.searchView)
                    searchViewModel.searchPeopleByPage(txt.query.toString(),++currentPage)
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