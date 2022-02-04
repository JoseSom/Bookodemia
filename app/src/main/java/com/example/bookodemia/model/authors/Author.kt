package com.example.bookodemia.model.authors

import com.example.bookodemia.model.books.Attributes
import com.example.bookodemia.model.books.Links
import com.example.bookodemia.model.books.Relationships
import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val type: String,
    val id: String,
) : java.io.Serializable
