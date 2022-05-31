package com.roque.rueda.gbm.coding.domain.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
open class TestBase {

    @get:org.junit.Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:org.junit.Rule
    val testCoroutineRule = TestCoroutineRule()

}