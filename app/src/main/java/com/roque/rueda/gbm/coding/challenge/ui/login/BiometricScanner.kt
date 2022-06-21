package com.roque.rueda.gbm.coding.challenge.ui.login

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import com.roque.rueda.gbm.coding.challenge.R
import java.util.concurrent.Executors

class BiometricScanner (private val fragment: Fragment) {

    private val context = fragment.requireContext()

    private val promptInfo: BiometricPrompt.PromptInfo
        get() =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle(fragment.getString(R.string.biometrics_title))
                .setDescription(fragment.getString(R.string.biometrics_description))
                .setNegativeButtonText(fragment.getString(R.string.biometrics_negative))
                .build()

    fun scan(callback: BiometricPrompt.AuthenticationCallback) {
        BiometricPrompt(
            fragment,
            Executors.newSingleThreadExecutor(),
            callback
        ).authenticate(promptInfo)
    }


}