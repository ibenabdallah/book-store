package com.ibenabdallah.bookstore.data.entity

data class DocsResponse<T>(
    val numFound: Int,
    val start: Int,
    val numFoundExact: Boolean,
    val docs: List<T>,
)

