package com.example.bookodemia.model.books

import kotlinx.serialization.Serializable

@Serializable
data class Books(
    val data: MutableList<Book>
): java.io.Serializable
