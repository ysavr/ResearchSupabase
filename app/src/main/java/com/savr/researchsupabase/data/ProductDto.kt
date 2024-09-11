package com.savr.researchsupabase.data

import com.savr.researchsupabase.core.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ProductDto(
    @SerialName("id")
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    @SerialName("name")
    val name: String?,
    @SerialName("price")
    val price: Double?,
    @SerialName("image")
    val imageUrl: String?,
    @SerialName("created_at")
    val createdAt: String?
)
