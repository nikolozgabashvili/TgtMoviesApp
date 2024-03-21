package com.example.tgtmoviesapp.application.presentation.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tgtmoviesapp.application.domain.models.DisplayIndicator
import com.example.tgtmoviesapp.application.presentation.adapters.searchAdapter.SearchAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.searchAdapter.ViewPagerAdapter
import com.example.tgtmoviesapp.application.presentation.fragments.celebrities.FoundCelebritiesFragment
import com.example.tgtmoviesapp.application.presentation.fragments.movies.FoundMoviesFragment
import com.example.tgtmoviesapp.application.presentation.fragments.tvShows.FoundShowsFragment
import com.example.tgtmoviesapp.application.presentation.viewModels.SearchViewModel
import com.example.tgtmoviesapp.databinding.FragmentSecondSearchBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecondSearchFragment : Fragment() {

    private var _binding: FragmentSecondSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchAdapter: SearchAdapter
    private lateinit var miniSearchRecyclerView: RecyclerView


    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private var movieSuccess = false
    private var tvSuccess = false
    private var celebritySuccess = false


    var fragmentList = mutableListOf<Fragment>()


    private val searchViewModel: SearchViewModel by activityViewModels()


    private lateinit var viewpagerAdapter: ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondSearchBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentList = mutableListOf()
        val searchViewText = arguments?.getString("searchviewText") ?: ""

        binding.clearButton.setOnClickListener {
            binding.searchView.setText("")
        }
        initViews()
        initAdapters()
        setupViewPager()
        setupObservers()
        search()

        if (searchViewText.isNotEmpty() && !searchViewModel.successList.value[0]) {

            binding.searchView.setText(searchViewText)
            setupFoundInfoDisplay()
        }



        binding.backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }


    }

    private fun setupViewPager() {
        viewpagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewpagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = viewpagerAdapter.getPageTitle(pos)

        }.attach()
    }

    private fun initViews() {
        binding.searchView.requestFocus()
        viewPager = binding.viewpager
        tabLayout = binding.tabLayout

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        val inputMethodManager = activity?.getSystemService(InputMethodManager::class.java)
        inputMethodManager?.showSoftInput(binding.searchView, InputMethodManager.SHOW_IMPLICIT)
    }

    fun setDefaultViews() {

        binding.tabLayout.visibility = View.INVISIBLE
        viewPager.visibility = View.INVISIBLE
        searchViewModel.movies.value = null
        searchViewModel.tvShows.value = null
        searchViewModel.people.value = null

    }


    private fun updateSearchAdapter(data: List<String>) {
        searchAdapter.setMovieList(data, DisplayIndicator.NONE)
    }

    private fun initAdapters() {

        searchAdapter = SearchAdapter()
        miniSearchRecyclerView = binding.predictiveSearchDisplay
        miniSearchRecyclerView.adapter = searchAdapter
        miniSearchRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        searchAdapter.onItemClick = {

            binding.searchView.setText(it)
            binding.searchView.onEditorAction(IME_ACTION_SEARCH)




        }

    }


    private fun setupObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchNameList.collect {
                if (!it.isNullOrEmpty())
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

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.movies.collect {
                it?.let {
                    it.data?.let { movies ->
                        movies.results?.let { lst ->
                            movieSuccess = lst.isNotEmpty()
                            if (movieSuccess) {
                                searchViewModel.passingList[0] = FoundMoviesFragment()
                            }
                            searchViewModel.successList.value[1] = true
                            searchViewModel.successList.value =
                                searchViewModel.successList.value.copyOf()

                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.tvShows.collect {
                it?.let {
                    it.data?.let { movies ->
                        movies.results?.let { lst ->
                            tvSuccess = lst.isNotEmpty()
                            if (tvSuccess) {
                                searchViewModel.passingList[1] = FoundShowsFragment()
                            }
                            searchViewModel.successList.value[0] = true
                            searchViewModel.successList.value =
                                searchViewModel.successList.value.copyOf()

                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.people.collect {
                it?.let { resource ->
                    resource.data?.let { movies ->
                        movies.results?.let { lst ->

                            celebritySuccess = lst.isNotEmpty()
                            if (celebritySuccess) {
                                searchViewModel.passingList[2] = FoundCelebritiesFragment()
                            }

                            searchViewModel.successList.value[2] = true
                            searchViewModel.successList.value =
                                searchViewModel.successList.value.copyOf()


                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.successList.collect {

                if (it[0] && it[1] && it[2]) {
                    val sortedFragmentArray = searchViewModel.passingList
                    var sortedList = mutableListOf<Fragment>()
                    for (i in sortedFragmentArray) {

                        if (i != null) {
                            sortedList.add(i)
                        }
                    }
                    if (sortedList.size == 3) {
                        sortedList = (listOf(FoundThingsFragment()) + sortedList).toMutableList()
                    }
                    val sortedHeaderList = mutableListOf<String>()
                    for (i in sortedList) {
                        when (i) {
                            is FoundThingsFragment -> sortedHeaderList.add("all")
                            is FoundMoviesFragment -> sortedHeaderList.add("movies")
                            is FoundShowsFragment -> sortedHeaderList.add("Tv")
                            is FoundCelebritiesFragment -> sortedHeaderList.add("Celebrities")
                        }
                    }
                    viewpagerAdapter.updateAdapter(sortedList, sortedHeaderList)
                    viewpagerAdapter.notifyDataSetChanged()
                    if (sortedList.size == 1)
                        setupSingleDisplay()
                    else if (sortedList.size != 0)
                        setupFoundInfoDisplay()


                }
            }
        }


    }

    private fun setupSingleDisplay() {
        viewPager.visibility = View.VISIBLE
        binding.tabLayout.visibility = View.GONE
        binding.predictiveSearchDisplay.visibility = View.INVISIBLE
        binding.blankTextView.visibility = View.INVISIBLE
    }

    private fun setupFoundInfoDisplay() {
        viewPager.visibility = View.VISIBLE
        binding.tabLayout.visibility = View.VISIBLE
        binding.predictiveSearchDisplay.visibility = View.INVISIBLE
        binding.blankTextView.visibility = View.INVISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


    private fun search() {

        binding.searchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId ==  IME_ACTION_SEARCH) {

                val q =  binding.searchView.text.toString()
                val inputMethodManager = activity?.getSystemService(InputMethodManager::class.java)
                inputMethodManager?.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
                searchViewModel.passingList = Array(3) { null }
                searchViewModel.successList.value =Array(3) { false }
                searchViewModel.movies.value = null
                searchViewModel.tvShows.value = null
                searchViewModel.people.value = null
                movieSuccess = false
                tvSuccess = false
                celebritySuccess = false
                searchViewModel.submittedText = q
                fragmentList = mutableListOf()
                searchViewModel.getEverythingByQuery(q)
                binding.searchView.clearFocus()
                setupViewPager()


                true
            } else {
                false
            }
        }

        binding.searchView.addTextChangedListener {
            val txt = binding.searchView.text.toString()
            if (searchViewModel.submittedText!=txt) {

                    searchViewModel.passingList = Array(3) { null }
                    searchViewModel.successList.value =Array(3) { false }
                    searchViewModel.movies.value = null
                    searchViewModel.tvShows.value = null
                    searchViewModel.people.value = null
                    movieSuccess = false
                    tvSuccess = false
                    celebritySuccess = false
                    searchViewModel.clearSubmittedText()
                    binding.predictiveSearchDisplay.visibility = View.VISIBLE
                    binding.blankTextView.visibility = View.INVISIBLE

                    setDefaultViews()

                    if (txt.isNotEmpty()) {
                        searchViewModel.cancelDebounceJob()
                        searchViewModel.startDebounceJob()
                        searchViewModel.setTextFlow(txt)
                    } else {
                        binding.blankTextView.visibility = View.VISIBLE
                        binding.predictiveSearchDisplay.visibility = View.INVISIBLE
                    }


                }

        }


    }

}

