package com.savr.researchsupabase.core.resource

sealed class Resource<out R> {
    data object Loading : Resource<Nothing>()

    data class Success<out T>(val data: T) : Resource<T>()

    data class Error<out T>(val code: Int? = null, val message: String?) : Resource<T>()

    data class Exception(val exception: java.lang.Exception) : Resource<Nothing>()
}
