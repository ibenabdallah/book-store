package com.ibenabdallah.bookstore.data.network

import com.ibenabdallah.bookstore.data.entity.DocEntity
import com.ibenabdallah.bookstore.data.entity.DocsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface BookStoreApi {

    @GET("/search.json?q=kotlin")
    suspend fun getAll(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<DocsResponse<DocEntity>>

}