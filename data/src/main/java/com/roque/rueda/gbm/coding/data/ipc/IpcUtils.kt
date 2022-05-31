package com.roque.rueda.gbm.coding.data.ipc

import com.roque.rueda.gbm.coding.domain.DomainResult
import com.roque.rueda.gbm.coding.domain.ipc.model.IpcEntry
import java.lang.IllegalStateException

fun DomainResult<List<MockyResponse.IpcIndexDTO>>.toDomain(): DomainResult<List<IpcEntry>> {
    if (this is DomainResult.Failure) throw IllegalStateException("Result is Failure: $this")
    return DomainResult.createSuccess(
        this.data!!.map {
            IpcEntry(
                date = it.date,
                price = it.price,
                percentageChange = it.percentageChange,
                volume = it.volume,
                change = it.change
            )
        }
    )
}