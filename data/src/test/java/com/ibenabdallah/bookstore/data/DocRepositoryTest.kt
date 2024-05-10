package com.ibenabdallah.bookstore.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.ibenabdallah.bookstore.data.mapper.DocMapper
import com.ibenabdallah.bookstore.data.entity.DocEntity
import com.ibenabdallah.bookstore.data.entity.DocsResponse
import com.ibenabdallah.bookstore.data.network.Status
import com.ibenabdallah.bookstore.data.remote.DocRemoteRetriever
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
class DocRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var wcRetriever: DocRemoteRetriever

    @MockK
    private lateinit var docMapper: DocMapper

    private lateinit var repository: DocRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = DocRepositoryImpl(wcRetriever, docMapper)
    }

    @Test
    fun `invoke should return Flow of PagingData`() = runTest {

        coEvery { docMapper.map(docEntity1) } returns bookModel
        coEvery { wcRetriever.invoke(0) } returns Status.Success(docsResponse)

        val flow: Flow<PagingData<BookModel>> = repository.invoke()

        assertEquals(pagingData.asSnapshot(), flow.asSnapshot())
    }

    @Test
    fun `invoke should return empty Flow of PagingData`() = runTest {

        coEvery { wcRetriever.invoke(0) } returns Status.Success(emptyDocsResponse)

        val flow: Flow<PagingData<BookModel>> = repository.invoke()

        assertEquals(emptyPagingData.asSnapshot(), flow.asSnapshot())
    }


    companion object {
        // Sample data
        private val docEntity1 = DocEntity(
                title = "Kotlin Programming", title_suggest = "Kotlin Programming",
                title_sort = "Kotlin Programming",
                cover_edition_key = "OL34780722M",
                author_name = listOf(
                    "Matthew Mathias",
                    "John Gallagher",
                    "David Greenhalgh",
                    "Josh Skeen"
                ),
                first_publish_year = 2018,
                publish_year = listOf("2018"),
                edition_count = 6,
                edition_key = listOf(
                    "OL35566818M",
                    "OL30597828M",
                    "OL35878975M",
                    "OL34738668M",
                    "OL40313230M",
                    "OL34780722M"
            )
        )

        val emptyPagingData = flowOf<PagingData<DocEntity>>()

        private val docsEntity = listOf(docEntity1)
        private val docsResponse = DocsResponse(
            docs = docsEntity,
            numFound = 10,
            start = 0,
            numFoundExact = true
        )
        private val emptyDocsResponse = DocsResponse(
            docs = listOf<DocEntity>(),
            numFound = 10,
            start = 0,
            numFoundExact = true
        )
        /**********/
        private val bookModel = BookModel(
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
        private val booksModel = listOf(bookModel)

        val pagingData = flowOf(PagingData.from(booksModel))
    }
}

