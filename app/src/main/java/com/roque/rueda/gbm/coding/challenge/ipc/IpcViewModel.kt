package com.roque.rueda.gbm.coding.challenge.ipc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.rueda.gbm.coding.domain.DomainResult
import com.roque.rueda.gbm.coding.domain.error.ErrorResult
import com.roque.rueda.gbm.coding.domain.ipc.model.IpcEntry
import com.roque.rueda.gbm.coding.domain.ipc.usecase.GetIpcUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IpcViewModel @Inject constructor(
    private val getIpcUseCase: GetIpcUseCase
) : ViewModel() {

    private val _iuState = MutableStateFlow<IpcIndexUiState>(IpcIndexUiState.Loading)
    val iuState: StateFlow<IpcIndexUiState> = _iuState

    fun fetchIpcIndex() {
        _iuState.value = IpcIndexUiState.Loading
        viewModelScope.launch {
            when(val result = getIpcUseCase()) {
                is DomainResult.Failure -> _iuState.value = IpcIndexUiState.Error(result.errorResult)
                is DomainResult.Success -> _iuState.value = IpcIndexUiState.Success(result.data)
            }
        }
    }
}

sealed class IpcIndexUiState {
    object Loading: IpcIndexUiState()
    data class Success(val ipcIndexList: List<IpcEntry>): IpcIndexUiState()
    data class Error(val errorResult: ErrorResult): IpcIndexUiState()
}