package com.ibenabdallah.bookstore.data.di

import com.ibenabdallah.bookstore.data.mapper.DocMapper
import com.ibenabdallah.bookstore.data.mapper.DocMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface MapperModule {

    @Binds
    @Singleton
    fun bindDocMapper(impl: DocMapperImpl): DocMapper

}