package com.savr.researchsupabase.presentation.feature.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savr.researchsupabase.core.resource.Resource
import com.savr.researchsupabase.core.state.UIState
import com.savr.researchsupabase.domain.AuthRepository
import com.savr.researchsupabase.domain.Product
import com.savr.researchsupabase.domain.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    companion object {
        private const val TAG = "ProductViewModel"
    }

    private val _productList = MutableStateFlow<List<Product>>(listOf())
    val productList: Flow<List<Product>> = _productList

    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState: StateFlow<UIState> = _uiState

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

    fun logout() {
        viewModelScope.launch {
            authRepository.logOut().collect {
                if (it is Resource.Error) {
                    _uiState.value = UIState.Failure(it.message.toString())
                }
                if (it is Resource.Exception) {
                    _uiState.value = UIState.Failure(it.exception.message.toString())
                }
                if (it is Resource.Success) {
                    _uiState.value = UIState.Success
                }
            }
        }
    }
}