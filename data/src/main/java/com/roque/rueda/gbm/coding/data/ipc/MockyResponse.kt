package com.roque.rueda.gbm.coding.data.ipc

import kotlinx.serialization.Serializable

class MockyResponse {

    @Serializable
    data class IpcIndexDTO (
        val date: String?,
        val price: Double?,
        val percentageChange: Double?,
        val volume: Long?,
        val change: Double?
    )
}