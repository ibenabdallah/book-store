package com.ibenabdallah.bookstore.data.remote

import com.ibenabdallah.bookstore.data.PAGE_SIZE
import com.ibenabdallah.bookstore.data.di.RemoteDataSourceCoroutineContext
import com.ibenabdallah.bookstore.data.entity.DocEntity
import com.ibenabdallah.bookstore.data.entity.DocsResponse
import com.ibenabdallah.bookstore.data.network.BookStoreApi
import com.ibenabdallah.bookstore.data.network.Status
import com.ibenabdallah.bookstore.data.network.toStatus
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal class DocRemoteRetrieverImpl @Inject constructor(
    private val api: BookStoreApi,
    @RemoteDataSourceCoroutineContext private val coroutineContext: CoroutineContext,
) :
    DocRemoteRetriever {

    override suspend fun invoke(currentPage: Int): Status<DocsResponse<DocEntity>> {
        return withContext(coroutineContext) {
            api.getAll(limit = PAGE_SIZE, offset = currentPage).toStatus { Status.Success(it) }
        }
    }
}