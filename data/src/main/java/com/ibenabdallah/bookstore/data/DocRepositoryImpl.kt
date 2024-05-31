package com.ibenabdallah.bookstore.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ibenabdallah.bookstore.data.mapper.DocMapper
import com.ibenabdallah.bookstore.data.entity.DocEntity
import com.ibenabdallah.bookstore.data.remote.DocRemoteRetriever
import com.ibenabdallah.bookstore.domain.model.BookModel
import com.ibenabdallah.bookstore.domain.repository.DocRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DocRepositoryImpl @Inject constructor(
    private val docRetriever: DocRemoteRetriever,
    private val mapper: DocMapper,
) :
    DocRepository {
    override fun invoke(): Flow<PagingData<BookModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                DocPagingSource { offset ->
                    docRetriever.invoke(offset)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map {
                mapper.map(it)
            }
        }
    }
}