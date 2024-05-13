package com.ibenabdallah.bookstore.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.ibenabdallah.bookstore.domain.model.BookModel
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

        val flow: Flow<PagingData<BookModel>> = useCase.invoke()

        assertEquals(outputPagingData.asSnapshot(), flow.asSnapshot())
    }

    @Test
    fun `invoke should return empty Flow of PagingData`() = runTest {

        coEvery { repository.invoke() } returns inputEmptyPagingData

        val flow: Flow<PagingData<BookModel>> = useCase.invoke()

        assertEquals(outputEmptyPagingData.asSnapshot(), flow.asSnapshot())
    }


    companion object {
        // Sample data
        private val bookModels = listOf(
            BookModel(
                title = "Kotlin Programming",
                titleSuggest = "Kotlin Programming",
                titleSort = "Kotlin Programming",
                cover = "OL34780722M",
                authorName = listOf(
                    "Matthew Mathias",
                    "John Gallagher",
                    "David Greenhalgh",
                    "Josh Skeen"
                ),
                firstPublishYear  = 2018,
                publishYear = listOf("2018"),
                editionCount = 6,
                editions = listOf(
                    "OL35566818M",
                    "OL30597828M",
                    "OL35878975M",
                    "OL34738668M",
                    "OL40313230M",
                    "OL34780722M"
                )
            ),
            BookModel(
                title = "Kotlin Programming",
                titleSuggest = "Kotlin Programming",
                titleSort = "Kotlin Programming",
                cover = "OL34780722M",
                authorName = listOf(
                    "Matthew Mathias",
                    "John Gallagher",
                    "David Greenhalgh",
                    "Josh Skeen"
                ),
                firstPublishYear  = 2018,
                publishYear = listOf("2018"),
                editionCount = 6,
                editions = listOf(
                    "OL35566818M",
                    "OL30597828M",
                    "OL35878975M",
                    "OL34738668M",
                    "OL40313230M",
                    "OL34780722M"
                )
            )
        )
        val inputPagingData = flowOf(PagingData.from(bookModels))
        val inputEmptyPagingData = flowOf<PagingData<BookModel>>()
        val outputPagingData = flowOf(PagingData.from(bookModels))
        val outputEmptyPagingData = flowOf<PagingData<BookModel>>()
    }
}

