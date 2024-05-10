package com.ibenabdallah.bookstore.domain.model


data class BookModel(
    val title: String,
    val titleSuggest: String,
    val titleSort: String,
    val cover: String?,
    val authorName: List<String>,
    val firstPublishYear: Int,
    val publishYear: List<String>,
    val editionCount: Int,
    val editions: List<String>,
)
