package com.ibenabdallah.bookstore.data.di

import com.ibenabdallah.bookstore.domain.repository.DocRepository
import com.ibenabdallah.bookstore.data.DocRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun bindDocRepository(impl: DocRepositoryImpl): DocRepository

}