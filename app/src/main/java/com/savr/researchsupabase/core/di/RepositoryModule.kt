package com.savr.researchsupabase.core.di

import com.savr.researchsupabase.data.AuthRepositoryImpl
import com.savr.researchsupabase.data.ProductRepositoryImpl
import com.savr.researchsupabase.domain.AuthRepository
import com.savr.researchsupabase.domain.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideProductRepository(
        supabaseClient: SupabaseClient,
    ): ProductRepository {
        return ProductRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        supabaseClient: SupabaseClient,
    ): AuthRepository {
        return AuthRepositoryImpl(supabaseClient)
    }
}