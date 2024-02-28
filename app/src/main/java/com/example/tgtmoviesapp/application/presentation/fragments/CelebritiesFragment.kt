package com.example.tgtmoviesapp.application.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.domain.models.Person
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.celebritiesAdapter.CelebritiesAdapter
import com.example.tgtmoviesapp.application.presentation.recyclerAdapters.celebritiesAdapter.TopRatedPeopleAdapter
import com.example.tgtmoviesapp.application.presentation.viewModels.CelebritiesViewModel
import com.example.tgtmoviesapp.databinding.FragmentCelebritiesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CelebritiesFragment : Fragment() {

    private var _binding:FragmentCelebritiesBinding? = null
    private val binding get() = _binding!!

    private lateinit var popularPersonAdapter1:CelebritiesAdapter
    private lateinit var popularPersonAdapter2:CelebritiesAdapter
    private lateinit var trendingAdapter:TopRatedPeopleAdapter
    private lateinit var popularRecycler1:RecyclerView
    private lateinit var popularRecycler2:RecyclerView
    private lateinit var trendingRecycler:RecyclerView

    private val celebViewModel : CelebritiesViewModel by activityViewModels ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        _binding = FragmentCelebritiesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
    }

    private fun initAdapters() {
        initPopularAdapters()
        initTrendingAdapters()
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            celebViewModel.popularPeople.collect{
                it?.let {
                    it.data?.let {
                        updatePopularAdapters(it)
                    }

                }
            }
        }

        lifecycleScope.launch {
            celebViewModel.trendingPeople.collect{
                it?.let {
                    it.data?.let {
                        updateTrendingAdapter(it)
                    }

                }
            }
        }
    }

    private fun updateTrendingAdapter(it: Person) {
        trendingAdapter.setPersonList(it)
    }

    private fun initPopularAdapters() {
        popularPersonAdapter1 = CelebritiesAdapter()
        popularPersonAdapter2 = CelebritiesAdapter()
        popularRecycler1 = binding.popularPersonRecycler1
        popularRecycler2 = binding.popularPersonRecycler2
        popularRecycler1.adapter = popularPersonAdapter1
        popularRecycler2.adapter = popularPersonAdapter2
        popularRecycler1.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        popularRecycler2.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }
    private fun initTrendingAdapters() {
        trendingAdapter = TopRatedPeopleAdapter()
        trendingRecycler = binding.trendingPeopleRecycler
        trendingRecycler.adapter = trendingAdapter
        trendingRecycler.layoutManager = GridLayoutManager(requireContext(),4,GridLayoutManager.HORIZONTAL,false)
    }

    private fun updatePopularAdapters(data:Person){
        val mainList = data.results
        var firstPart = listOf<Person.Result?>()
        var secondPart = listOf<Person.Result?>()
        mainList?.let {
            firstPart = it.subList(0,it.size/2)
            secondPart = it.subList(it.size/2,it.size)
        }
        popularPersonAdapter1.setCelebList(firstPart)
        popularPersonAdapter2.setCelebList(secondPart)

    }


}