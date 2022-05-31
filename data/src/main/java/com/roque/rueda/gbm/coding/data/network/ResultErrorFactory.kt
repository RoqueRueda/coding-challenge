package com.roque.rueda.gbm.coding.data.network

import android.content.Context
import com.roque.rueda.gbm.coding.domain.error.ErrorResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResultErrorFactory @Inject constructor(
    private val jsonParser: Json,
    @ApplicationContext private val context: Context
) {

    fun create(response: Response<*>): ErrorResult {
        return try {
            val responseError = response.errorBody()?.string()?.trim().orEmpty()
            ErrorResult(code = response.code(), message = responseError)
        } catch (ex: Exception) {
            ErrorResult(message = ex.message.orEmpty(), cause = ex)
        }
    }
}
