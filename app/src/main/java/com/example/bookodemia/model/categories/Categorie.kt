package com.example.bookodemia.model.categories

import kotlinx.serialization.Serializable

@Serializable
data class Categorie(
    val type: String,
    val id: String,
) : java.io.Serializable
