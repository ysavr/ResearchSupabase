package com.savr.researchsupabase.presentation.feature.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savr.researchsupabase.core.resource.Resource
import com.savr.researchsupabase.core.state.UIState
import com.savr.researchsupabase.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    companion object {
        private const val TAG = "SignUpViewModel"
    }

    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState: StateFlow<UIState> = _uiState

    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password = _password

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun signUp() {
        viewModelScope.launch {
            authRepository.signUp(_email.value, _password.value).collect {
                when (it) {
                    is Resource.Error -> {
                        _uiState.value = UIState.Failure(it.message.toString())
                        Log.e(TAG, "signUp: ${it.message}")
                    }
                    is Resource.Exception -> {
                        _uiState.value = UIState.Failure(it.exception.message.toString())
                        Log.e(TAG, "signUp: ${it.exception}")
                    }
                    is Resource.Loading -> Log.d(TAG, "signUp: Loading")
                    is Resource.Success -> {
                        _uiState.value = UIState.Success
                        Log.d(TAG, "signUp: ${it.data}")
                    }
                }
            }
        }
    }

}