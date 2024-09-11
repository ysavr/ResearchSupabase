package com.savr.researchsupabase.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String?,
    @SerialName("price")
    val price: Double?,
    @SerialName("image")
    val imageUrl: String?,
    @SerialName("created_at")
    val createdAt: String?
)
