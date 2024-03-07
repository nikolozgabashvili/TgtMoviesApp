package com.example.tgtmoviesapp.application.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.presentation.viewModels.UserViewModel
import com.example.tgtmoviesapp.databinding.FragmentAuthenticationBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AuthenticationFragment : Fragment() {

    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = _binding!!

    private var args: Boolean? = false
    private lateinit var signButton:Button
    private lateinit var usernameField:EditText
    private lateinit var passwordField:EditText
    private lateinit var repeatPasswordField:EditText

    private val userViewModel :UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        args = arguments?.getBoolean("setCancellable", false)
        setupListeners()
        setupObservers()

    }

    private fun initViews() {
        usernameField = binding.usernameInput
        repeatPasswordField = binding.repeatPasswordInput
        passwordField = binding.passwordInput
        signButton = binding.signButton
    }

    private fun setupListeners() {
        signButton.text = getString(R.string.sign_up)
        binding.accountText.text = getString(R.string.do_not_have_account)
        repeatPasswordField.visibility=View.GONE
        binding.signInButton.text = getString(R.string.sign_in)
        binding.signInButton.setOnClickListener {
            loginUser()
        }

        if (args == true) {
            binding.backToProfileButton.visibility = View.VISIBLE
            binding.backToProfileButton.setOnClickListener { findNavController().popBackStack() }
            binding.guestButton.visibility = View.GONE
        } else {
            binding.backToProfileButton.visibility = View.INVISIBLE
            binding.guestButton.visibility = View.VISIBLE
        }
        binding.guestButton.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.mainFragment)
        }
        signButton.setOnClickListener {
            if (signButton.text==getString(R.string.sign_up)){
                signButton.text = getString(R.string.sign_in)
                binding.accountText.text = getString(R.string.already_have_account)
                repeatPasswordField.visibility=View.VISIBLE
                binding.signInButton.text = getString(R.string.sign_up)
                binding.signInButton.setOnClickListener {
                    registerNewUser()
                }
            }else{
                signButton.text = getString(R.string.sign_up)
                binding.accountText.text = getString(R.string.do_not_have_account)
                repeatPasswordField.visibility=View.GONE
                binding.signInButton.text = getString(R.string.sign_in)
                binding.signInButton.setOnClickListener {
                    loginUser()
                }
            }
        }

    }
    private fun setupObservers(){
        lifecycleScope.launch {
            userViewModel.loginResource.collect{

            }
        }
    }

    private fun loginUser() {
        if (usernameField.text.isNotEmpty()&&passwordField.text.isNotEmpty())
        userViewModel.loginUser(usernameField.text.toString(),passwordField.text.toString())
        if(userViewModel.currentUserToken.isNotEmpty()){
            findNavController().popBackStack()
            findNavController().navigate(R.id.mainFragment)
        }


    }

    private fun registerNewUser() {
        //TODO register new user
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}