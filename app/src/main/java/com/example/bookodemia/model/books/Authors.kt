package com.example.bookodemia.model.books

import kotlinx.serialization.Serializable

@Serializable
data class Authors(
    val links: Links
): java.io.Serializable
