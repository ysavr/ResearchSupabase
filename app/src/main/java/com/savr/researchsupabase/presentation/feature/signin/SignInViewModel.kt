package com.savr.researchsupabase.presentation.feature.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savr.researchsupabase.core.resource.Resource
import com.savr.researchsupabase.core.state.UIState
import com.savr.researchsupabase.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    companion object {
        private const val TAG = "SignInViewModel"
    }

    private val _uiState = MutableSharedFlow<UIState>()
    val uiState: SharedFlow<UIState> = _uiState

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

    fun signIn() {
        viewModelScope.launch {
            authRepository.signUp(_email.value, _password.value).collect {
                when (it) {
                    is Resource.Error -> {
                        _uiState.emit(UIState.Failure(it.message.toString()))
                        Log.e(TAG, "signIn: ${it.message}")
                    }
                    is Resource.Exception -> {
                        _uiState.emit(UIState.Failure(it.exception.message.toString()))
                        Log.e(TAG, "signIn: ${it.exception}")
                    }
                    is Resource.Loading -> Log.d(TAG, "signIn: Loading")
                    is Resource.Success -> {
                        _uiState.emit(UIState.Success)
                        Log.d(TAG, "signIn: ${it.data}")
                    }
                }
            }
        }
    }
}