package com.savr.researchsupabase.data

import com.savr.researchsupabase.core.resource.Resource
import com.savr.researchsupabase.domain.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
) : AuthRepository {

    override fun signUp(mail: String, pass: String): Flow<Resource<UserInfo>> {
        return flow {
            try {
                emit(Resource.Loading)
                val user = supabaseClient.auth.signUpWith(Email) {
                    email = mail
                    password = pass
                }
                if (user != null) {
                    emit(Resource.Success(user))
                } else {
                    emit(Resource.Error(message = "Sign up failed"))
                }
            } catch (e: Exception) {
                emit(Resource.Exception(e))
            }

        }.flowOn(Dispatchers.IO)
    }

    override fun signIn(mail: String, pass: String): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading)
                supabaseClient.auth.signInWith(Email) {
                    email = mail
                    password = pass
                }
                val accessToken = supabaseClient.auth.currentAccessTokenOrNull() ?: ""
                emit(Resource.Success(accessToken))
            } catch (e: Exception) {
                emit(Resource.Exception(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun logOut(): Flow<Resource<Boolean>> {
        return flow {
            try {
                emit(Resource.Loading)
                supabaseClient.auth.signOut()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Exception(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}