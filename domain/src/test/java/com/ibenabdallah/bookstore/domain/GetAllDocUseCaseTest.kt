package com.ibenabdallah.bookstore.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.ibenabdallah.bookstore.domain.repository.DocRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAllDocUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: DocRepository

    private lateinit var useCase: GetAllDocUseCase


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetAllDocUseCaseImpl(repository)
    }

    @Test
    fun `invoke should return Flow of PagingData`() = runTest {

        coEvery { repository.invoke() } returns inputPagingData

        val flow: Flow<PagingData<WcItem>> = useCase.invoke()

        assertEquals(outputPagingData.asSnapshot(), flow.asSnapshot())
    }

    @Test
    fun `invoke should return empty Flow of PagingData`() = runTest {

        coEvery { repository.invoke() } returns inputEmptyPagingData

        val flow: Flow<PagingData<WcItem>> = useCase.invoke()

        assertEquals(outputEmptyPagingData.asSnapshot(), flow.asSnapshot())
    }


    companion object {
        // Sample data
        private val inputWcItems = listOf(
            WcItem(
                type = "TOILETTES",
                address = "JARDIN DURANTON",
                borough = 75015,
                hourly = "24h / 24",
                accessPmr = "Non"
            ),
            WcItem(
                type = "TOILETTES",
                address = "JARDIN DURANTON",
                borough = 75015,
                hourly = "24h / 24",
                accessPmr = "Non"
            )
        )
        val inputPagingData = flowOf(PagingData.from(inputWcItems))
        val inputEmptyPagingData = flowOf<PagingData<WcItem>>()

        private val outputWcItems = listOf(
            WcItem(
                type = "TOILETTES",
                address = "JARDIN DURANTON",
                borough = 75015,
                hourly = "24h / 24",
                accessPmr = "Non"
            ),
            WcItem(
                type = "TOILETTES",
                address = "JARDIN DURANTON",
                borough = 75015,
                hourly = "24h / 24",
                accessPmr = "Non"
            )
        )
        val outputPagingData = flowOf(PagingData.from(outputWcItems))
        val outputEmptyPagingData = flowOf<PagingData<WcItem>>()
    }
}

