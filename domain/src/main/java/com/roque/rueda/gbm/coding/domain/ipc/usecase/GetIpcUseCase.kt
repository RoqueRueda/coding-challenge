package com.roque.rueda.gbm.coding.domain.ipc.usecase

import com.roque.rueda.gbm.coding.domain.DomainResult
import com.roque.rueda.gbm.coding.domain.error.ErrorResult
import com.roque.rueda.gbm.coding.domain.error.NoConnectivityException
import com.roque.rueda.gbm.coding.domain.ipc.model.IpcEntry
import com.roque.rueda.gbm.coding.domain.ipc.repository.IpcRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetIpcUseCase @Inject constructor(
    private val ipcRepository: IpcRepository
) {

    suspend operator fun invoke() : DomainResult<List<IpcEntry>> {
        return try {
            ipcRepository.fetchIpcIndex()
        } catch (ex: Exception) {
            DomainResult.createFailure(
                ErrorResult(
                    code = ErrorResult.UNKNOWN_ERROR,
                    message = ex.message.orEmpty()
                )
            )
        }
    }
}