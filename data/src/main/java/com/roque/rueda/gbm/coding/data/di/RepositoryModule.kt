package com.roque.rueda.gbm.coding.data.di

import com.roque.rueda.gbm.coding.data.ipc.IpcMockyRepository
import com.roque.rueda.gbm.coding.data.network.ResultErrorFactory
import com.roque.rueda.gbm.coding.domain.ipc.repository.IpcRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsIpcRepository(repository: IpcMockyRepository): IpcRepository

}
