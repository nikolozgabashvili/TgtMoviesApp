package com.example.tgtmoviesapp.application.presentation.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tgtmoviesapp.application.presentation.viewModels.UserViewModel
import com.example.tgtmoviesapp.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()
    private var userToken: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.authButton.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToAuthenticationFragment2(
                    true
                )
            )
        }
        binding.signOutButton.setOnClickListener {
            userViewModel.removeTokenValue()
        }
        setupObservers()


    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.currentUserToken.collect {
                if (it == null) {
                    binding.signOutButton.visibility = View.GONE
                    binding.authButton.visibility = View.VISIBLE
                } else {
                    binding.signOutButton.visibility = View.VISIBLE
                    binding.authButton.visibility = View.GONE
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.currentUserName.collect {

                    binding.username.text = it?:"guest"

            }
        }
    }


}