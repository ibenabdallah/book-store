package com.ibenabdallah.bookstore.domain.di

import com.ibenabdallah.bookstore.domain.GetAllDocUseCase
import com.ibenabdallah.bookstore.domain.GetAllDocUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
internal interface UseCaseModule {

    @Binds
    fun bindGetAllDocUseCase(impl: GetAllDocUseCaseImpl): GetAllDocUseCase
}