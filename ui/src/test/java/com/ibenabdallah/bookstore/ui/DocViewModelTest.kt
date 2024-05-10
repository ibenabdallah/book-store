package com.ibenabdallah.bookstore.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.ibenabdallah.bookstore.domain.GetAllDocUseCase
import com.ibenabdallah.bookstore.domain.model.BookModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DocViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var useCase: GetAllDocUseCase

    private val testDispatcher: CoroutineDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: DocViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = DocViewModel(useCase)
    }


    @Test
    fun `getAll should return Flow of PagingData`() = runTest {
        testScope.launch {

            coEvery { useCase() } returns pagingData

            val flow: Flow<PagingData<BookModel>> = viewModel.getAll()

            assertEquals(pagingData.asSnapshot(), flow.asSnapshot())

        }
    }

    @Test
    fun `getAll should return empty of PagingData`() = runTest {
        testScope.launch {

            coEvery { useCase() } returns flowOf(PagingData.empty())

            val flow: Flow<PagingData<BookModel>> = viewModel.getAll()

            assertEquals(pagingData.asSnapshot(), flow.asSnapshot())

        }
    }

    companion object {

        val pagingData: Flow<PagingData<BookModel>> = flowOf(
            PagingData.from(
                listOf(
                    BookModel(
                        title = "Kotlin",
                        titleSuggest = "Kotlin",
                        titleSort = "Kotlin",
                        cover = "OL32905578M",
                        authorName = listOf(
                            "Bruce Eckel",
                            "Svetlana Isakova"
                        ),
                        firstPublishYear = 2021,
                        publishYear = listOf("2021"),
                        editionCount = 3,
                        editions = listOf(
                            "OL38809009M",
                            "OL38795112M",
                            "OL38805620M"
                        )
                    )
                )
            )
        )
    }
}