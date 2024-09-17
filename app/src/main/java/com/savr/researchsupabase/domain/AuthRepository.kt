package com.savr.researchsupabase.domain

import com.savr.researchsupabase.core.resource.Resource
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signUp(mail: String, pass: String): Flow<Resource<UserInfo>>
    fun signIn(mail: String, pass: String): Flow<Resource<String>>
    fun logOut(): Flow<Resource<Boolean>>
}