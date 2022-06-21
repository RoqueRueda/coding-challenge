package com.roque.rueda.gbm.coding.challenge.ui.ipc

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

    private val _uiState = MutableStateFlow<IpcIndexUIState>(IpcIndexUIState.Loading)
    val uiState: StateFlow<IpcIndexUIState> = _uiState

    fun fetchIpcIndex() {
        _uiState.value = IpcIndexUIState.Loading
        viewModelScope.launch {
            when(val result = getIpcUseCase()) {
                is DomainResult.Failure -> _uiState.value =
                    IpcIndexUIState.Error(result.errorResult)
                is DomainResult.Success -> _uiState.value = IpcIndexUIState.Success(result.data)
            }
        }
    }
}

sealed class IpcIndexUIState {
    object Loading: IpcIndexUIState()
    data class Success(val ipcIndexList: List<IpcEntry>): IpcIndexUIState()
    data class Error(val errorResult: ErrorResult): IpcIndexUIState()
}