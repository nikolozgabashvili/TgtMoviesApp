package com.example.tgtmoviesapp.application.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navhost = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navhost.findNavController()

        val bottomNavView =binding.bottomNavigationView
//        bottomNavView.setupWithNavController(navController)


        bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.moviesFragment -> {
                    navController.navigate(R.id.moviesFragment)
                    true
                }
                R.id.tvShowFragment -> {
                    navController.navigate(R.id.tvShowFragment)
                    true
                }
                R.id.celebritiesFragment -> {
                    navController.navigate(R.id.celebritiesFragment)
                    true
                }
                else -> false
            }
        }

        bottomNavView.setOnItemReselectedListener {

        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavView.menu.findItem(destination.id)?.isChecked = true
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}