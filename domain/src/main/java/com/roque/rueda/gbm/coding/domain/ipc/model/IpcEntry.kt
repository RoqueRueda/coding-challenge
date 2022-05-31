package com.roque.rueda.gbm.coding.domain.ipc.model

data class IpcEntry (
    val date: String?,
    val price: Double?,
    val percentageChange: Double?,
    val volume: Long?,
    val change: Double?
)
