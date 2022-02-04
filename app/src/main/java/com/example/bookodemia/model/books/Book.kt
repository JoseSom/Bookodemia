package com.example.bookodemia.model.books

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val type: String,
    val id: String,
    val attributes: Attributes,
    val relationships: Relationships,
    val links: Links,
    val image: String = ""
): java.io.Serializable
