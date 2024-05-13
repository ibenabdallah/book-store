package com.ibenabdallah.bookstore.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.ibenabdallah.bookstore.data.entity.DocEntity
import com.ibenabdallah.bookstore.data.entity.DocsResponse
import com.ibenabdallah.bookstore.data.network.Status
import com.ibenabdallah.bookstore.data.remote.DocRemoteRetriever
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DocPagingSourceTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var docRetriever: DocRemoteRetriever

    private lateinit var docPagingSource: DocPagingSource<DocEntity>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        docPagingSource = DocPagingSource { offset ->
            docRetriever.invoke(offset)
        }
    }

    @Test
    fun `Docs paging source load - failure - http error`() = runTest {
        val error = RuntimeException("404", Throwable())

        coEvery { docRetriever.invoke(0) } throws error

        val expectedResult = PagingSource.LoadResult.Error<Int, DocEntity>(error)

        assertEquals(
            expectedResult.toString(), docPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

    @Test
    fun `Docs paging source refresh - Error`() = runTest {

        val dataError = Status.Error<DocsResponse<DocEntity>>(Throwable())
        coEvery { docRetriever.invoke(0) } returns dataError

        val expectedResult = PagingSource.LoadResult.Error<Int, DocEntity>(dataError.error)

        assertEquals(
            expectedResult,
            docPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `Docs paging source refresh - success`() = runTest {

        coEvery { docRetriever.invoke(0) } returns Status.Success(docsResponse)

        val expectedResult = PagingSource.LoadResult.Page<Int, DocEntity>(
            data = docsResponse.docs,
            prevKey = null,
            nextKey = 1
        )

        assertEquals(
            expectedResult,
            docPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `Docs paging source append - success`() = runTest {

        coEvery { docRetriever.invoke(0) } returns Status.Success(docsResponse)

        val expectedResult = PagingSource.LoadResult.Page<Int, DocEntity>(
            data = docsResponse.docs,
            prevKey = null,
            nextKey = 1
        )

        assertEquals(
            expectedResult, docPagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `Docs paging source prepend - success`() = runTest {

        coEvery { docRetriever.invoke(0) } returns Status.Success(docsResponse)

        val expectedResult = PagingSource.LoadResult.Page<Int, DocEntity>(
            data = docsResponse.docs,
            prevKey = null,
            nextKey = 1
        )
        val actualResult = docPagingSource.load(
            PagingSource.LoadParams.Prepend(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        assertEquals(
            expectedResult,
            actualResult
        )
    }

    companion object {
        private val docsResponse = DocsResponse(
            docs = listOf(
                DocEntity(
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
            ),
            start = 1,
            numFound = 10,
            numFoundExact = true
        )
    }
}