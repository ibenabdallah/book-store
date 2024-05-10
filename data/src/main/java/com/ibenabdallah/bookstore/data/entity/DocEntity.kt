package com.ibenabdallah.bookstore.data.entity


data class DocEntity(
    val title: String,
    val title_suggest: String,
    val title_sort: String,
    val cover_edition_key: String?,
    val author_name: List<String>,
    val first_publish_year: Int,
    val publish_year: List<String>,
    val edition_count: Int,
    val edition_key: List<String>,
)