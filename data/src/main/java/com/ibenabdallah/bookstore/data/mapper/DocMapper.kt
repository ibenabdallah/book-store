package com.ibenabdallah.bookstore.data.mapper

import com.ibenabdallah.bookstore.data.entity.DocEntity
import com.ibenabdallah.bookstore.domain.model.BookModel

interface DocMapper {
    fun map(item: DocEntity): BookModel
}