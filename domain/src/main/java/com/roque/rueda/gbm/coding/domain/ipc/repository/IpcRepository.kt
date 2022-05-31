package com.roque.rueda.gbm.coding.domain.ipc.repository

import com.roque.rueda.gbm.coding.domain.ipc.model.IpcEntry

interface IpcRepository {

    suspend fun fetchIpcIndex(): List<IpcEntry>

}
