package com.roque.rueda.gbm.coding.data.ipc

import retrofit2.Response
import retrofit2.http.GET

interface MockyApi {

    @GET("/v3/cc4c350b-1f11-42a0-a1aa-f8593eafeb1e")
    suspend fun getIpc(): Response<List<MockyResponse.IpcIndexDTO>>
}