package com.roque.rueda.gbm.coding.challenge.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.rueda.gbm.coding.domain.login.usecase.BiometricsSupportedUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val biometricsSupportedUseCase: BiometricsSupportedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.BiometricSupported)
    val uiState: StateFlow<LoginUIState> = _uiState

    fun checkBiometricEnable() {
        _uiState.value = LoginUIState.Loading
        viewModelScope.launch {
            _uiState.value = if (biometricsSupportedUseCase()) {
                LoginUIState.BiometricSupported
            } else {
                LoginUIState.BiometricNotSupported
            }
        }
    }

//    fun login() {

//        _uiState.value = LoginUIState.Failed
//        // can be launched in a separate asynchronous job
//        val result = loginRepository.login(username, password)
//
//        if (result is Result.Success) {
//            _loginResult.value =
//                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
//    }

//    fun loginDataChanged(username: String, password: String) {
//        if (!isUserNameValid(username)) {
//            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
//        } else if (!isPasswordValid(password)) {
//            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
//        } else {
//            _loginForm.value = LoginFormState(isDataValid = true)
//        }
//    }

    fun authenticateWithBiometrics() {
        _uiState.value = LoginUIState.DisplayBiometricScanner
    }

    fun biometricsSucceeded() {
        _uiState.value = LoginUIState.Succeeded
    }

    fun biometricsFailed() {
        _uiState.value = LoginUIState.Failed
    }

    fun biometricsError() {
        _uiState.value = LoginUIState.Error
    }
}

sealed class LoginUIState {
    object BiometricSupported : LoginUIState()
    object BiometricNotSupported : LoginUIState()
    object Succeeded : LoginUIState()
    object Error : LoginUIState()
    object Failed : LoginUIState()
    object Loading : LoginUIState()
    object DisplayBiometricScanner: LoginUIState()
}