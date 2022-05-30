package com.roque.rueda.gbm.coding.domain

import com.roque.rueda.gbm.coding.domain.error.ErrorResult

sealed class DomainResult<out TResult : Any> {

    data class Success<out TResult : Any>(val resultData: TResult) : DomainResult<TResult>() {
        override val data: TResult = resultData
        override var status: DomainStatus = DomainStatus.SUCCESS
    }

    data class Failure(val errorResult: ErrorResult) : DomainResult<Nothing>() {
        override val data: Nothing? = null
        override val status: DomainStatus = DomainStatus.FAILURE
    }

    // Abstract properties
    abstract val data: TResult?
    abstract val status: DomainStatus

    companion object {
        fun <TResult : Any> createSuccess(value: TResult): DomainResult<TResult> =
            Success(value)

        fun createFailure(errorResult: ErrorResult): DomainResult<Nothing> =
            Failure(errorResult)
    }
}