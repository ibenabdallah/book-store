package com.ibenabdallah.bookstore.domain.repository

import androidx.paging.PagingData
import com.ibenabdallah.bookstore.domain.model.BookModel
import kotlinx.coroutines.flow.Flow

interface DocRepository {

    operator fun invoke(): Flow<PagingData<BookModel>>
}