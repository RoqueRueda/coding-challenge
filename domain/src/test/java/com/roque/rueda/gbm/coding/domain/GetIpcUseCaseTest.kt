package com.roque.rueda.gbm.coding.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.roque.rueda.gbm.coding.domain.base.TestBase
import com.roque.rueda.gbm.coding.domain.error.ErrorResult
import com.roque.rueda.gbm.coding.domain.error.NoConnectivityException
import com.roque.rueda.gbm.coding.domain.ipc.model.IpcEntry
import com.roque.rueda.gbm.coding.domain.ipc.repository.IpcRepository
import com.roque.rueda.gbm.coding.domain.ipc.usecase.GetIpcUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
    fun `when there is an exception return a failure`() = runBlocking {
        // Arrange
        val expected = ErrorResult(code = ErrorResult.UNKNOWN_ERROR, message = "")
        whenever(repository.fetchIpcIndex()).thenThrow(RuntimeException())

        // Act
        val useCase = GetIpcUseCase(repository)
        val actual = useCase()

        // Assert
        Assert.assertTrue(actual is DomainResult.Failure)
        val failure: DomainResult.Failure = actual as DomainResult.Failure
        Assert.assertEquals(expected.code, failure.errorResult.code)
    }

    @Test
    fun `retrieve the ipc entries`(): Unit = runBlocking {
        // Arrange
        val expected = DomainResult.createSuccess(
            listOf(
                IpcEntry(
                    date = "2020-08-18T00:02:43.91-05:00",
                    price = 39285.85,
                    percentageChange = 0.86257,
                    volume = 128684937,
                    change = 335.97
                )
            )
        )

        whenever(repository.fetchIpcIndex()).thenReturn(expected)
        val useCase = GetIpcUseCase(repository)

        // Act
        val actual = useCase()

        // Assert
        Assert.assertTrue(actual is DomainResult.Success)
        Assert.assertEquals(expected.data, actual.data)
    }
}