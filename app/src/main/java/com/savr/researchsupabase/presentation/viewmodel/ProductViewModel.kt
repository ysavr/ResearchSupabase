package com.savr.researchsupabase.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savr.researchsupabase.domain.Product
import com.savr.researchsupabase.domain.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _productList = MutableStateFlow<List<Product>>(listOf())
    val productList: Flow<List<Product>> = _productList

    fun getProducts() {
        viewModelScope.launch {
            val products = productRepository.getProducts().map {
                Product(
                    it.id,
                    it.name ?: "",
                    it.price ?: 0.0,
                    it.imageUrl ?: "",
                    it.createdAt ?: ""
                )
            }
            _productList.emit(products)
        }
    }

}