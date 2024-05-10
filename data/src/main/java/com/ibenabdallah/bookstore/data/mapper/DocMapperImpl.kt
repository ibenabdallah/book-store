package com.ibenabdallah.bookstore.data.mapper

import com.ibenabdallah.bookstore.data.entity.DocEntity
import com.ibenabdallah.bookstore.domain.model.BookModel
import javax.inject.Inject

class DocMapperImpl @Inject constructor(): DocMapper {
    override fun map(item: DocEntity): BookModel {
        return BookModel(
            title = item.title,
            titleSort = item.title_sort,
            titleSuggest = item.title_suggest,
            authorName = item.author_name,
            firstPublishYear = item.first_publish_year,
            publishYear = item.publish_year,
            editionCount = item.edition_count,
            editions = item.edition_key,
            cover = item.cover_edition_key?.getCoverImage(),
        )
    }

    private fun String.getCoverImage() = "https://covers.openlibrary.org/b/olid/$this-M.jpg"

}