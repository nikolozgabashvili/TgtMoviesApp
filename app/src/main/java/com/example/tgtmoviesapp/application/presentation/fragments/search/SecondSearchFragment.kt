package com.example.tgtmoviesapp.application.presentation.fragments.search

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
import androidx.viewpager2.widget.ViewPager2
import com.example.tgtmoviesapp.application.presentation.adapters.searchAdapter.SearchAdapter
import com.example.tgtmoviesapp.application.presentation.adapters.searchAdapter.ViewPagerAdapter
import com.example.tgtmoviesapp.application.presentation.fragments.movies.MoviesFragment
import com.example.tgtmoviesapp.application.presentation.viewModels.SearchViewModel
import com.example.tgtmoviesapp.databinding.FragmentSecondSearchBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class SecondSearchFragment : Fragment() {
    private lateinit var searchView: android.widget.SearchView
    private var _binding: FragmentSecondSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchAdapter: SearchAdapter
    private lateinit var miniSearchRecyclerView: RecyclerView


    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private var movieSuccess = false
    private var tvSuccess = false
    private var celebritySuccess = false

    var successList = MutableStateFlow(arrayOf<Boolean>())
    var passingList = arrayOf<Fragment?>()


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
        successList.value = Array(3) { false }
        passingList = Array(3) { null }
        val searchViewText = arguments?.getString("searchviewText") ?: ""

        initViews()
        initAdapters()
        setupViewPager()
        setupObservers()
        search()


        if (searchViewText.isNotEmpty())
            searchView.setQuery(searchViewText, false)

        binding.backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

    }

    private fun setupViewPager() {
        viewpagerAdapter = ViewPagerAdapter(requireParentFragment())
        viewPager.adapter = viewpagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = viewpagerAdapter.getPageTitle(pos)

        }.attach()
    }

    private fun initViews() {
        searchView = binding.searchView
        viewPager = binding.viewpager
        tabLayout = binding.tabLayout
        searchView.isIconified = false
    }

    fun setDefaultViews() {
        binding.tabLayout.visibility = View.INVISIBLE
        viewPager.visibility = View.INVISIBLE

        searchViewModel.movies.value = null
        searchViewModel.tvShows.value = null
        searchViewModel.people.value = null

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
        searchAdapter.onItemClick = {

            searchView.setQuery(it, true)

        }

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
                        movies.results?.let { lst ->
                            movieSuccess = lst.isNotEmpty()
                            successList.value[1] = true
                            successList.value = successList.value.copyOf()

                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            searchViewModel.tvShows.collect {
                it?.let {
                    it.data?.let { movies ->
                        movies.results?.let { lst ->
                            tvSuccess = lst.isNotEmpty()

                            successList.value[0] = true
                            successList.value = successList.value.copyOf()

                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            searchViewModel.people.collect {
                it?.let { resource ->
                    resource.data?.let { movies ->
                        movies.results?.let { lst ->

                            celebritySuccess = lst.isNotEmpty()

                            successList.value[2] = true
                            successList.value = successList.value.copyOf()


                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            successList.collect {
                println("mapcollected")
                if (it[0] && it[1] && it[2]) {
                    viewpagerAdapter.updateAdapter(movieSuccess, tvSuccess, celebritySuccess)
                    if (movieSuccess && !celebritySuccess && !tvSuccess) {
                        setupSingleDisplay()
                    } else if (celebritySuccess && !movieSuccess && !tvSuccess) {
                        setupSingleDisplay()
                    } else if (!celebritySuccess && !movieSuccess && tvSuccess) {
                        setupSingleDisplay()

                    } else if (!celebritySuccess && !movieSuccess) {
                        setDefaultViews()
                    } else {
                        setupFoundInfoDisplay()
                    }
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

        setDefaultViews()


        _binding = null
    }


    private fun search() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val q = query ?: ""
                searchViewModel.getEverythingByQuery(q)
                successList.value = Array(3) { false }

                setupViewPager()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val txt = newText ?: ""
                binding.predictiveSearchDisplay.visibility = View.VISIBLE
                binding.blankTextView.visibility = View.INVISIBLE
                if (txt.isNotEmpty()) {
                    searchViewModel.cancelDebounceJob()
                    searchViewModel.startDebounceJob()
                    searchViewModel.setTextFlow(txt)
                } else {
                    binding.blankTextView.visibility = View.VISIBLE
                    binding.predictiveSearchDisplay.visibility = View.INVISIBLE
                }

                setDefaultViews()
                searchViewModel.clearFoundList()
                return true

            }

        })

    }

}

private fun Array<Boolean>.isAllTrue(): Boolean {
    return this.all {
        it
    }
}