package com.savr.researchsupabase.presentation.feature.addproduct

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savr.researchsupabase.core.resource.Resource
import com.savr.researchsupabase.core.state.UIState
import com.savr.researchsupabase.data.ProductDto
import com.savr.researchsupabase.domain.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    companion object {
        private const val TAG = "AddProductViewModel"
    }

    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState: StateFlow<UIState> = _uiState

    private val _name = MutableStateFlow("")
    val name: Flow<String> = _name

    private val _price = MutableStateFlow(0.0)
    val price: Flow<Double> = _price

    private val _imageUrl = MutableStateFlow("")
    val imageUrl: Flow<String> = _imageUrl

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onPriceChange(price: Double) {
        _price.value = price
    }

    fun onImageChange(url: String) {
        _imageUrl.value = url
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createProduct(image: ByteArray) {
        viewModelScope.launch {
            val dateTime = ZonedDateTime.now()
            val formattedDateTime =
                dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSZ"))

            productRepository.uploadProductImage("${_name.value}.png", image).collect {
                when (it) {
                    Resource.Loading -> Log.d(TAG, "createProduct: Loading")
                    is Resource.Error -> {
                        _uiState.value = UIState.Failure(it.message.toString())
                    }

                    is Resource.Exception -> {
                        _uiState.value = UIState.Failure(it.toString())
                    }

                    is Resource.Success -> {
                        it.data.let { imgUrl ->
                            val product = ProductDto(
                                name = _name.value,
                                price = _price.value,
                                imageUrl = imgUrl,
                                createdAt = formattedDateTime
                            )
                            productRepository.createProduct(product).collect { createProduct ->
                                when (createProduct) {
                                    Resource.Loading -> Log.d(TAG, "createProduct: Loading")
                                    is Resource.Error -> {
                                        _uiState.value =
                                            UIState.Failure(createProduct.message.toString())
                                    }

                                    is Resource.Exception -> {
                                        _uiState.value = UIState.Failure(createProduct.toString())
                                    }

                                    is Resource.Success -> _uiState.update { UIState.Success }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}