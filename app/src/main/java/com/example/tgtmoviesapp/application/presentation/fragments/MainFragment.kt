package com.example.tgtmoviesapp.application.presentation.fragments

import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.VelocityTracker
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.commons.resource.Resource
import com.example.tgtmoviesapp.application.domain.models.TvShows
import com.example.tgtmoviesapp.application.presentation.viewModels.CelebritiesViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.MoviesViewModel
import com.example.tgtmoviesapp.application.presentation.viewModels.TvShowsViewModel
import com.example.tgtmoviesapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val moviesViewModel: MoviesViewModel by activityViewModels()
    private val tvShowsViewModel: TvShowsViewModel by activityViewModels()
    private val celebritiesViewModel: CelebritiesViewModel by activityViewModels()


    private val _loadingList : MutableStateFlow<Array<Boolean>> = MutableStateFlow(Array(11) { true })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()

        val navHost = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.findNavController()

        val bottomNavView =binding.bottomNavigationView

//        bottomNavView.setupWithNavController(navController)




        bottomNavView.setOnItemSelectedListener { menuItem ->
            try {
                if (menuItem.itemId ==R.id.searchFragment){
                    navController.navigate(R.id.searchFragment)
                }else {

                    navController.navigate(menuItem.itemId)

                }
                true

            }catch (e:Exception){

                false
            }
            bottomNavView.setBackgroundColor(Color.green(R.color.green))
            true


        }

        bottomNavView.setOnItemReselectedListener {
            if (it.itemId!=navController.currentDestination?.id) {
                //Todo pop fragments from pages backstack
                navController.popBackStack()
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.secondSearchFragment){
                bottomNavView.menu.findItem(R.id.searchFragment)?.isChecked = true

            }
            bottomNavView.menu.findItem(destination.id)?.isChecked = true
        }





    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.movies.collect{ resource->
                resource?.let {
                    if (it.loading !=true){
                        _loadingList.value[0] = false
                        _loadingList.value = _loadingList.value.copyOf()
                }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.upcomingMovies.collect{resource->
                resource?.let {
                    if (it.loading !=true){
                        _loadingList.value[1] = false
                        _loadingList.value = _loadingList.value.copyOf()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.topRatedMovies.collect{resource->
                resource?.let {
                    if (it.loading !=true){
                        _loadingList.value[2] = false
                        _loadingList.value = _loadingList.value.copyOf()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.trendingMovies.collect{resource->
                resource?.let {
                    if (it.loading !=true){
                        _loadingList.value[3] = false
                        _loadingList.value = _loadingList.value.copyOf()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.pITMovies.collect{resource->
                resource?.let {
                    if (it.loading !=true){
                        _loadingList.value[4] = false
                        _loadingList.value = _loadingList.value.copyOf()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            tvShowsViewModel.trendingTvShows.collect{resource->
                resource?.let {
                    if (it.loading !=true){
                        _loadingList.value[5] = false
                        _loadingList.value = _loadingList.value.copyOf()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            tvShowsViewModel.upcomingTvShows.collect{resource->
                resource?.let {
                    if (it.loading !=true){
                        _loadingList.value[6] = false
                        _loadingList.value = _loadingList.value.copyOf()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            tvShowsViewModel.popularTvShows.collect{resource->
                resource?.let {
                    if (it.loading !=true){
                        _loadingList.value[7] = false
                        _loadingList.value = _loadingList.value.copyOf()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            tvShowsViewModel.topRatedTvShows.collect{resource->
                resource?.let {
                    if (it.loading !=true){
                        _loadingList.value[8] = false
                        _loadingList.value = _loadingList.value.copyOf()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            celebritiesViewModel.popularPeople.collect{resource->
                resource?.let {
                    if (it.loading !=true){
                        _loadingList.value[9] = false
                        _loadingList.value = _loadingList.value.copyOf()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            celebritiesViewModel.trendingPeople.collect{resource->
                resource?.let {
                    if (it.loading !=true){
                        _loadingList.value[10] = false
                        _loadingList.value = _loadingList.value.copyOf()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            _loadingList.collect{
                it.map {
                }
                if (!it.contains(true)) {
                    binding.bottomNavigationView.visibility = VISIBLE
                    binding.loadingBar.visibility = GONE
                    binding.navHostFragment.visibility = VISIBLE
                }
            }


        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}