package com.savr.researchsupabase.core.state

sealed class UIState {
    data object Idle : UIState()
    data object Success : UIState()
    data class Failure(val message: String) : UIState()
}