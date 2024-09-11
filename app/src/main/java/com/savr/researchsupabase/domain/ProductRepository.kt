package com.savr.researchsupabase.domain

import com.savr.researchsupabase.data.ProductDto

interface ProductRepository {
    suspend fun getProducts(): List<ProductDto>
}