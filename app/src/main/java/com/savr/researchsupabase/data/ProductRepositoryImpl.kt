package com.savr.researchsupabase.data

import com.savr.researchsupabase.BuildConfig
import com.savr.researchsupabase.core.BUCKET_PRODUCTS
import com.savr.researchsupabase.core.TABLE_PRODUCTS
import com.savr.researchsupabase.domain.ProductRepository
import com.savr.researchsupabase.core.resource.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
) : ProductRepository {

    override suspend fun getProducts(): List<ProductDto> {
        return supabaseClient.from(TABLE_PRODUCTS).select().decodeList<ProductDto>()
    }

    override suspend fun createProduct(product: ProductDto): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading)
            try {
                supabaseClient.from(TABLE_PRODUCTS).insert(product)
                emit(Resource.Success("Product created successfully"))
            } catch (e: Exception) {
                emit(Resource.Exception(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun uploadProductImage(
        imageName: String,
        imageFile: ByteArray
    ): Flow<Resource<String>> {
        return flow {
            try {
                val url = supabaseClient.storage.from(BUCKET_PRODUCTS).upload(imageName, imageFile)
                emit(Resource.Success(buildImageUrl(url.path)))
            } catch (e: Exception) {
                emit(Resource.Exception(e))
            }
        }
    }

    private fun buildImageUrl(imageFileName: String): String {
        return "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/$BUCKET_PRODUCTS/${imageFileName}"
    }
}