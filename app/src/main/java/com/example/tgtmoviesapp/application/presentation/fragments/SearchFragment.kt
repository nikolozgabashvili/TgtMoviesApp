package com.example.tgtmoviesapp.application.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.presentation.viewModels.MoviesViewModel
import com.example.tgtmoviesapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding:FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val movieVieModel:MoviesViewModel by activityViewModels ()
    private var trendingList = listOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectTrendingNames()

        binding.searchButton.setOnClickListener{
            val action = SearchFragmentDirections.actionSearchFragment2ToSecondSearchFragment("NONE")
            findNavController().navigate(R.id.action_searchFragment2_to_secondSearchFragment)

        }
    }

    private fun collectTrendingNames() {
        lifecycleScope.launch {
            val movieNameList = mutableListOf<String?>()
            movieVieModel.trendingMovies.collect{resource->
                resource?.let {
                    val lst = it.data
                    lst?.results?.map {result->
                        movieNameList.add(result?.title ?: result?.originalTitle ?: "movie...")
                    }
                    setTrendingList(movieNameList)
                }

            }


        }
    }

    private fun setTrendingList(movieNameList: List<String?>) {
        if (movieNameList.size>=8) {
            binding.trendingMovie1.text = movieNameList[0]
            binding.trendingMovie2.text = movieNameList[1]
            binding.trendingMovie3.text = movieNameList[2]
            binding.trendingMovie4.text = movieNameList[3]
            binding.trendingMovie5.text = movieNameList[4]
            binding.trendingMovie6.text = movieNameList[5]
            binding.trendingMovie7.text = movieNameList[6]
            binding.trendingMovie8.text = movieNameList[7]
        }
    }


}