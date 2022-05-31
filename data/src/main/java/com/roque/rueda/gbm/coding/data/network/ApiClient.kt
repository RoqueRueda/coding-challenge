package com.roque.rueda.gbm.coding.data.network

import com.roque.rueda.gbm.coding.domain.DomainResult
import com.roque.rueda.gbm.coding.domain.error.ErrorResult
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApiClient @Inject constructor(private val errorFactory: ResultErrorFactory) {

    suspend fun <T : Any> tryRequestValue(call: suspend () -> Response<T>): DomainResult<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return DomainResult.createSuccess(response.body()!!)
            } else {
                DomainResult.createFailure(errorFactory.create(response))
            }
        } catch (ex: UnknownHostException) {
            DomainResult.createFailure(
                ErrorResult(
                    code = ErrorResult.NO_CONNECTION,
                    message = ex.message.orEmpty()
                )
            )
        } catch (ex: IOException) {
            DomainResult.createFailure(ErrorResult(message = ex.message.orEmpty()))
        } catch (ex: NullPointerException) {
            DomainResult.createFailure(ErrorResult(message = ex.message.orEmpty()))
        }
    }

    suspend fun tryRequestIgnoreResult(call: suspend () -> Response<Unit>): DomainResult<Unit> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                DomainResult.createSuccess(Unit)
            } else {
                DomainResult.createFailure(errorFactory.create(response))
            }
        } catch (ex: UnknownHostException) {
            DomainResult.createFailure(
                ErrorResult(
                    code = ErrorResult.NO_CONNECTION,
                    message = ex.message.orEmpty()
                )
            )
        } catch (ex: IOException) {
            DomainResult.createFailure(ErrorResult(message = ex.message.orEmpty()))
        } catch (ex: NullPointerException) {
            DomainResult.createFailure(ErrorResult(message = ex.message.orEmpty()))
        }
    }

}