package com.roque.rueda.gbm.coding.domain.login.usecase

import android.content.Context
import androidx.biometric.BiometricManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BiometricsSupportedUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val biometricsRequirement = BiometricManager.Authenticators.BIOMETRIC_STRONG

    suspend operator fun invoke(): Boolean =
        withContext(ioDispatcher) {
            val biometricManager = BiometricManager.from(context)
            val canAuthenticate = biometricManager.canAuthenticate(biometricsRequirement)
            canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS
        }

}