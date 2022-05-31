package com.roque.rueda.gbm.coding.domain.ipc.usecase

import com.roque.rueda.gbm.coding.domain.DomainResult
import com.roque.rueda.gbm.coding.domain.error.ErrorResult
import com.roque.rueda.gbm.coding.domain.error.NoConnectivityException
import com.roque.rueda.gbm.coding.domain.ipc.model.IpcEntry
import com.roque.rueda.gbm.coding.domain.ipc.repository.IpcRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetIpcUseCase @Inject constructor(
    private val ipcRepository: IpcRepository
) {

    @Throws(NoConnectivityException::class)
    suspend operator fun invoke() : DomainResult<List<IpcEntry>> {
        return try {
            val result = ipcRepository.fetchIpcIndex()
            DomainResult.createSuccess(result)
        } catch (ex: NoConnectivityException) {
            DomainResult.createFailure(
                ErrorResult(
                    code = ErrorResult.CODE_NO_CONNECTION,
                    message = ex.message.orEmpty()
                )
            )
        }
    }
}