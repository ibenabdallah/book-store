package com.ibenabdallah.bookstore.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ibenabdallah.bookstore.data.entity.DocsResponse
import com.ibenabdallah.bookstore.data.network.Status

const val PAGE_SIZE = 20

internal class DocsPagingSource<T: Any>(
    private val fetch: suspend (offset: Int) -> Status<DocsResponse<T>>,
) : PagingSource<Int, T>() {


    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {

            val currentPage = params.getCurrentPage()
            val prevKey = getPreviousKey(currentPage)


            return fetch(currentPage).let {
                when (it) {
                    is Status.Success -> {
                        val nexKey =  params.getNextKey(it.data.numFound, currentPage)
                        LoadResult.Page(
                            data = it.data.docs,
                            prevKey = prevKey,
                            nextKey = nexKey,
                        )
                    }

                    is Status.Error -> LoadResult.Error(it.error)
                }
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun getPreviousKey(currentPage: Int) =
        if (currentPage == FIRST_PAGE_INDEX) null else currentPage - 1

    private fun LoadParams<Int>.getCurrentPage() = this.key ?: FIRST_PAGE_INDEX

    private fun LoadParams<Int>.getNextKey(totalCount: Int, currentPage: Int) =
        if (this.loadSize < totalCount) currentPage + 1 else null


    companion object {
        const val FIRST_PAGE_INDEX = 0
    }
}
