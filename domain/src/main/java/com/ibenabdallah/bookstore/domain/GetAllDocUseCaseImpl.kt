package com.ibenabdallah.bookstore.domain

import androidx.paging.PagingData
import com.ibenabdallah.bookstore.domain.model.BookModel
import com.ibenabdallah.bookstore.domain.repository.DocRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDocUseCaseImpl @Inject constructor(private val repository: DocRepository) :
    GetAllDocUseCase {
    override fun invoke(): Flow<PagingData<BookModel>> = repository()

}