package com.ibenabdallah.bookstore.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

@Qualifier
annotation class RemoteDataSourceCoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {

    @Provides
    @RemoteDataSourceCoroutineContext
    fun provideRemoteDataSourceCoroutineContext(): CoroutineContext =
        Dispatchers.IO + CoroutineName("Remote Data Source")
}
