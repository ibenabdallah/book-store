package com.ibenabdallah.bookstore.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ibenabdallah.bookstore.data.entity.DocEntity
import com.ibenabdallah.bookstore.data.entity.DocsResponse
import com.ibenabdallah.bookstore.data.network.BookStoreApi
import com.ibenabdallah.bookstore.data.network.Status
import com.ibenabdallah.bookstore.data.remote.DocRemoteRetriever
import com.ibenabdallah.bookstore.data.remote.DocRemoteRetrieverImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class DocRemoteRetrieverTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var api: BookStoreApi

    private lateinit var wcRetriever: DocRemoteRetriever

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        wcRetriever = DocRemoteRetrieverImpl(api, testDispatcher)
    }

    @Test
    fun `WCs paging source load - failure - http error`() = runTest(testDispatcher) {

        val error = Response.error<DocsResponse<DocEntity>>(404, "null".toResponseBody())

        coEvery { api.getAll(limit = PAGE_SIZE, offset = 0 * PAGE_SIZE) } returns error

        val expectedResult = Status.Error<DocsResponse<DocEntity>>(Throwable(error.message()))

        val actualResult = wcRetriever.invoke(0)

        assertEquals(
            expectedResult.toString(),
            actualResult.toString()
        )
    }


    @Test
    fun `WCs paging source refresh - success`() = runTest(testDispatcher) {

        coEvery { api.getAll(limit = PAGE_SIZE, offset = 0 * PAGE_SIZE) } returns Response.success(
            docsResponse
        )

        val expectedResult = Status.Success(docsResponse)

        assertEquals(
            expectedResult,
            wcRetriever.invoke(0)
        )
    }

    @Test
    fun `WCs paging source append - success`() = runTest(testDispatcher) {

        coEvery { api.getAll(limit = PAGE_SIZE, offset = 0 * PAGE_SIZE) } returns Response.success(
            docsResponse
        )

        val expectedResult = Status.Success(docsResponse)

        assertEquals(
            expectedResult, wcRetriever.invoke(0)
        )
    }

    @Test
    fun `WCs paging source prepend - success`() = runTest(testDispatcher) {

        coEvery { api.getAll(limit = PAGE_SIZE, offset = 0 * PAGE_SIZE) } returns Response.success(
            docsResponse
        )

        val expectedResult = Status.Success(docsResponse)

        assertEquals(
            expectedResult,
            wcRetriever.invoke(0)
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