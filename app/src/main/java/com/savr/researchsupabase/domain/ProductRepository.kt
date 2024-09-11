package com.savr.researchsupabase.domain

import com.savr.researchsupabase.data.ProductDto
import com.savr.researchsupabase.core.resource.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): List<ProductDto>
    suspend fun createProduct(product: ProductDto): Flow<Resource<String>>
    suspend fun uploadProductImage(
        imageName: String,
        imageFile: ByteArray
    ): Flow<Resource<String>>
}