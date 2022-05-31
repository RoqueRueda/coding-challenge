package com.roque.rueda.gbm.coding.data.ipc

import com.roque.rueda.gbm.coding.data.network.ApiClient
import com.roque.rueda.gbm.coding.domain.DomainResult
import com.roque.rueda.gbm.coding.domain.ipc.model.IpcEntry
import com.roque.rueda.gbm.coding.domain.ipc.repository.IpcRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IpcMockyRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val mockyApi: MockyApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IpcRepository {

    override suspend fun fetchIpcIndex(): DomainResult<List<IpcEntry>> =
        withContext(ioDispatcher) {
            val apiResponse = apiClient.tryRequestValue {
                mockyApi.getIpc()
            }

            return@withContext when(apiResponse) {
                is DomainResult.Failure -> apiResponse
                is DomainResult.Success -> apiResponse.toDomain()
            }
        }


}