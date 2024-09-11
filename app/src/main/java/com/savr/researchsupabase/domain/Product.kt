package com.savr.researchsupabase.domain

import java.util.UUID

data class Product(
    val id: UUID,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val createdAt: String
)
