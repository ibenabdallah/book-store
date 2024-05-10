package com.ibenabdallah.bookstore.data.remote

import com.ibenabdallah.bookstore.data.entity.DocEntity
import com.ibenabdallah.bookstore.data.entity.DocsResponse
import com.ibenabdallah.bookstore.data.network.Status

interface DocRemoteRetriever {
    suspend operator fun invoke(currentPage : Int): Status<DocsResponse<DocEntity>>
}