package com.cse234.assets.auth

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
   suspend fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    suspend fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
}