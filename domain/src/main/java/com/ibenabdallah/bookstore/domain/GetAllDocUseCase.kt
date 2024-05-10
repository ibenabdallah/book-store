package com.ibenabdallah.bookstore.domain

import androidx.paging.PagingData
import com.ibenabdallah.bookstore.domain.model.BookModel
import kotlinx.coroutines.flow.Flow

interface GetAllDocUseCase {
    operator fun invoke(): Flow<PagingData<BookModel>>
}