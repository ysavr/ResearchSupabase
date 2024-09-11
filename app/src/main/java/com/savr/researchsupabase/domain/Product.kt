package com.savr.researchsupabase.domain

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val createdAt: String
)
