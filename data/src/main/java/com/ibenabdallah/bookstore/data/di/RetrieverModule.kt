package com.ibenabdallah.bookstore.data.di

import com.ibenabdallah.bookstore.data.remote.DocRemoteRetriever
import com.ibenabdallah.bookstore.data.remote.DocRemoteRetrieverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RetrieverModule {

    @Binds
    @Singleton
    fun bindDocRetriever(impl: DocRemoteRetrieverImpl): DocRemoteRetriever

}