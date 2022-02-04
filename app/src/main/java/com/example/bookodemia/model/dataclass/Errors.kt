package com.example.bookodemia.model.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Errors(
    val errors: List<Error>
)
