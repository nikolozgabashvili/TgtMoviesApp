package com.example.tgtmoviesapp.application.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tgtmoviesapp.R
import com.example.tgtmoviesapp.application.presentation.viewModels.UserViewModel
import com.example.tgtmoviesapp.databinding.FragmentAuthenticationBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AuthenticationFragment : Fragment() {

    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = _binding!!

    private var args: Boolean? = false

    private lateinit var signButton: Button
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var repeatPasswordField: EditText

    private lateinit var mailField: EditText
    private lateinit var repeatPasswordFieldLayout: TextInputLayout
    private lateinit var mailFieldLayout: TextInputLayout

    private val userViewModel: UserViewModel by activityViewModels()

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

        args = arguments?.getBoolean("setCancellable", false) ?: false

        setupListeners()
        setupObservers()

    }

    private fun initViews() {
        usernameField = binding.usernameInput
        repeatPasswordFieldLayout = binding.repeatPasswordInputLayout
        repeatPasswordField = binding.repeatPasswordInput
        mailField = binding.mailInput
        mailFieldLayout = binding.mailInputLayout
        passwordField = binding.passwordInput
        signButton = binding.signButton
    }


    private fun setupListeners() {

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
            clearTexts()
            findNavController().popBackStack()
            findNavController().navigate(R.id.mainFragment)
        }
        signButton.setOnClickListener {
            clearErrors()
            clearTexts()
            if (signButton.text == getString(R.string.sign_up)) {
                signButton.text = getString(R.string.sign_in)
                binding.accountText.text = getString(R.string.already_have_account)
                repeatPasswordFieldLayout.visibility = View.VISIBLE
                mailFieldLayout.visibility = View.VISIBLE
                binding.signInButton.text = getString(R.string.sign_up)
                binding.signInButton.setOnClickListener {
                    registerNewUser()
                }
            } else {
                signButton.text = getString(R.string.sign_up)
                binding.accountText.text = getString(R.string.do_not_have_account)
                repeatPasswordFieldLayout.visibility = View.GONE
                mailFieldLayout.visibility = View.GONE
                binding.signInButton.text = getString(R.string.sign_in)
                binding.signInButton.setOnClickListener {
                    loginUser()
                }
            }
        }

    }

    private fun setupObservers() {

        viewLifecycleOwner.lifecycleScope.launch {

        }
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.registerResource.collect {

                if (it?.message != null) {
                    it.message.map { str ->

                        when (str?.trim()) {
                            "Password must be at least 8 characters long" -> binding.passwordInputLayout.error =
                                "Password must be at least 8 characters long"

                            "Password must contain at least one uppercase letter" -> binding.passwordInputLayout.error =
                                "Password must contain at least one uppercase letter"

                            "Password must contain at least one lowercase letter" -> binding.passwordInputLayout.error =
                                "Password must contain at least one lowercase letter"

                            "Password must contain at least one digit" -> binding.passwordInputLayout.error =
                                "Password must contain at least one digit"

                            "User already exists" -> binding.usernameInputLayout.error =
                                "User already exists"

                            "Email already exists" -> mailFieldLayout.error = "Email already exists"
                            "Invalid email address format" -> binding.mailInputLayout.error =
                                "Invalid email address format"
                        }
                    }
                } else if (it?.data != null) {
                    clearTexts()
                    Toast.makeText(
                        requireContext(),
                        "account created successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.loginResource.collect {

                if (it?.message != null) {

                    if (it.message.size == 1) {
                        it.message.map {
                            when (it?.trim()) {

                                "Invalid Username or Password" -> {
                                    mailFieldLayout.error = "Invalid Username or Password"
                                    binding.passwordInputLayout.error =
                                        "Invalid Username or Password"
                                }

                            }
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner
                .repeatOnLifecycle(
                    Lifecycle.State.STARTED
                )
                {
                    userViewModel.checkToken.collect {
                        it.let {
                            if (it) {
                                if (args == true) {
                                    findNavController().popBackStack()

                                } else {
                                    findNavController().popBackStack()
                                    findNavController().navigate(R.id.mainFragment)

                                }
                            }

                        }

                    }
                }

        }
    }


    private fun enter() {
        findNavController().popBackStack()
        findNavController().navigate(R.id.mainFragment)
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

    private fun loginUser() {

        clearErrors()


        if (usernameField.text.isNotEmpty() && passwordField.text.isNotEmpty())
            userViewModel.loginUser(usernameField.text.toString(), passwordField.text.toString())
        else if (usernameField.text.isEmpty())
            binding.usernameInputLayout.error = "username shouldn't be empty"
        else
            binding.passwordInputLayout.error = "password shouldn't be empty"


    }

    private fun registerNewUser() {
        clearErrors()
        if (usernameField.text.isNotEmpty() && passwordField.text.length >= 8 && passwordField.text.toString() == repeatPasswordField.text.toString() && mailField.text.isNotEmpty())
            userViewModel.registerUser(
                usernameField.text.toString(),
                passwordField.text.toString(),
                mailField.text.toString()
            )
        if (passwordField.text.length < 8) {
            binding.passwordInputLayout.error = "Password must be at least 8 characters long"
        }
        if (passwordField.text.toString() != repeatPasswordField.text.toString()) {
            repeatPasswordFieldLayout.error = "passwords doesn't match"
            binding.passwordInputLayout.error = "passwords doesn't match"
        }

    }

    private fun clearErrors() {
        binding.passwordInputLayout.error = null
        binding.repeatPasswordInputLayout.error = null
        binding.mailInputLayout.error = null
        binding.usernameInputLayout.error = null
    }

    private fun clearTexts() {
        binding.repeatPasswordInput.setText("")
        binding.passwordInput.setText("")
        binding.mailInput.setText("")
        binding.usernameInput.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}