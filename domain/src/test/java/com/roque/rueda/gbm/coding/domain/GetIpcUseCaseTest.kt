package com.roque.rueda.gbm.coding.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.roque.rueda.gbm.coding.domain.DomainResult
import com.roque.rueda.gbm.coding.domain.base.TestBase
import com.roque.rueda.gbm.coding.domain.error.ErrorResult
import com.roque.rueda.gbm.coding.domain.error.NoConnectivityException
import com.roque.rueda.gbm.coding.domain.ipc.repository.IpcRepository
import com.roque.rueda.gbm.coding.domain.ipc.usecase.GetIpcUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetIpcUseCaseTest : TestBase() {

    private lateinit var repository: IpcRepository

    @Before
    fun setUp() {
        repository = mock()
    }

    @Test
    fun `when there is no connection should return a failure`() = runBlocking {
        // Arrange
        val expected = ErrorResult(code = ErrorResult.CODE_NO_CONNECTION, message = "")
        whenever(repository.fetchIpcIndex()).thenThrow(NoConnectivityException())

        // Act
        val useCase = GetIpcUseCase(repository)
        val actual = useCase()

        // Assert
        Assert.assertTrue(actual is DomainResult.Failure)
        val failure: DomainResult.Failure = actual as DomainResult.Failure
        Assert.assertEquals(expected.code, failure.errorResult.code)

    }
}