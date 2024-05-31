package com.ibenabdallah.bookstore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ibenabdallah.bookstore.domain.GetAllDocUseCase
import com.ibenabdallah.bookstore.domain.model.BookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class DocViewModel @Inject constructor(private val useCase: GetAllDocUseCase) : ViewModel() {

    fun getAll(): Flow<PagingData<BookModel>> {
        return useCase()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
    }
}