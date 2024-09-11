package com.savr.researchsupabase.data

import com.savr.researchsupabase.domain.ProductRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
) : ProductRepository {

    override suspend fun getProducts(): List<ProductDto> {
        return supabaseClient.from("products").select().decodeList<ProductDto>()
    }
}