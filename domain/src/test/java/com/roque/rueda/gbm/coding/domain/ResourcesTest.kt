package com.roque.rueda.gbm.coding.domain

import com.roque.rueda.gbm.coding.domain.DomainResult
import com.roque.rueda.gbm.coding.domain.DomainStatus
import com.roque.rueda.gbm.coding.domain.error.ErrorResult
import org.junit.Assert
import org.junit.Test
import kotlin.Exception

class ResourcesTest {


    @Test fun `when a result is success the status should be success`() {
        // Arrange
        val expected = DomainStatus.SUCCESS

        // Act
        val resource = DomainResult.createSuccess(Any())

        // Assert
        Assert.assertEquals(expected, resource.status)
    }

    @Test fun `when a result is created and its success data should not be null`() {

        // Act
        val resource = DomainResult.createSuccess(Any())

        // Assert
        Assert.assertNotNull(resource.data)
    }

    @Test fun `result is success can return some data of the defined type`() {
        // Arrange
        val someData = listOf("1", "2", "3")

        // Act
        val result = DomainResult.createSuccess(someData)

        // Assert
        Assert.assertTrue(result is DomainResult.Success)
        Assert.assertEquals(someData, result.data)
    }

    @Test fun `when result is a failure then status should be failure`() {
        // Arrange
        val expected = DomainStatus.FAILURE

        // Act
        val result = DomainResult.createFailure(ErrorResult(message = ""))

        // Assert
        Assert.assertEquals(expected, result.status)
    }

}