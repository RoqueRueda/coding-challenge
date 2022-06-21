package com.roque.rueda.gbm.coding.challenge.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.roque.rueda.gbm.coding.challenge.R
import com.roque.rueda.gbm.coding.challenge.databinding.FragmentLoginBinding
import com.roque.rueda.gbm.coding.challenge.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authenticateButton = binding.authenticateWithBiometrics

        lifecycleScope.launchWhenResumed {
            loginViewModel.checkBiometricEnable()
        }

        lifecycleScope.launch {
            loginViewModel.uiState.collect {
                render(it)
            }
        }

        authenticateButton.setOnClickListener {
            loginViewModel.authenticateWithBiometrics()
        }
    }

    private fun render(uiState: LoginUIState) {
        removeLoadingIndicator()
        when (uiState) {
            LoginUIState.BiometricNotSupported -> {
                showBiometricsNoSupported()
            }
            LoginUIState.BiometricSupported -> {
                showBiometricsSupported()
            }
            LoginUIState.Error -> {
                showGenericError()
            }
            LoginUIState.Failed -> {
                showLoginFailed()
            }
            LoginUIState.Succeeded -> {
                navigateToMain()
            }
            LoginUIState.Loading -> {
                showLoadingIndicator()
            }
            LoginUIState.DisplayBiometricScanner -> {
                showBiometricsScanner()
            }
        }
    }

    private fun navigateToMain() {
        requireActivity().finishAndRemoveTask()
        requireActivity().startActivity(
            Intent(
                activity,
                MainActivity::class.java
            )
        )
    }

    private fun showGenericError() {
        Snackbar.make(binding.root, getString(R.string.authentication_error), Snackbar.LENGTH_INDEFINITE)
            .setAnchorView(binding.authenticateWithBiometrics)
            .setAction(getString(R.string.retry)) { showBiometricsScanner() }
            .show()
    }

    private fun showBiometricsScanner() {
        val scanner = BiometricScanner(this)
        scanner.scan(object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                loginViewModel.biometricsSucceeded()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                loginViewModel.biometricsFailed()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                loginViewModel.biometricsError()
            }
        })
    }

    private fun showBiometricsSupported() {
        removeLoadingIndicator()
        binding.authenticateWithBiometrics.isEnabled = true
    }

    private fun showBiometricsNoSupported() {
        binding.authenticateWithBiometrics.isEnabled = false
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToBiometricsNotSupportedFragment()
        )
    }

    private fun removeLoadingIndicator() {
        binding.loading.isVisible = false
    }

    private fun showLoadingIndicator() {
        binding.loading.isVisible = true
    }

    private fun showLoginFailed() {
        Snackbar.make(binding.root, getString(R.string.authentication_failed), Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.authenticateWithBiometrics)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}