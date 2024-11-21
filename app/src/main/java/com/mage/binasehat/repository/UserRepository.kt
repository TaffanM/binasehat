package com.mage.binasehat.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mage.binasehat.data.remote.api.UserApiService
import com.mage.binasehat.data.remote.model.FormSubmissionRequest
import com.mage.binasehat.data.remote.model.LoginRequest
import com.mage.binasehat.data.remote.model.RegisterRequest
import com.mage.binasehat.data.remote.response.DetailUserResponse
import com.mage.binasehat.data.remote.response.LoginResponse
import com.mage.binasehat.data.remote.response.RegisterResponse
import com.mage.binasehat.data.remote.response.UploadResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userApiService: UserApiService,
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun register(username: String, email: String, password: String): RegisterResponse {
        return withContext(dispatcher) {
            val registerRequest = RegisterRequest(username, email, password)
            userApiService.register(registerRequest)
        }
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return withContext(dispatcher) {
            val response = userApiService.login(loginRequest)
            saveUserToken(response.token)  // Save token after successful login
            response
        }
    }



    private suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token  // Save token to DataStore
        }
    }

    suspend fun getUserToken(): String? {
        return dataStore.data.first()[USER_TOKEN_KEY]  // Retrieve token from DataStore
    }

    suspend fun upload(authToken: String, photo: MultipartBody.Part): UploadResponse {
        return withContext(dispatcher) {
            val tokenWithBearer = "Bearer $authToken"
            userApiService.upload(tokenWithBearer, photo)
        }
    }

    suspend fun getDetailUser(authToken: String): DetailUserResponse {
        return withContext(dispatcher) {
            val tokenWithBearer = "Bearer $authToken"
            userApiService.getUserDetail(tokenWithBearer)
        }
    }

    suspend fun submitForm(authToken: String, birt: String, gender: String, tall: Float, weigh: Float) {
        withContext(dispatcher) {
            val formSubmissionRequest = FormSubmissionRequest(birt, gender, tall, weigh)
            userApiService.submitForm(authToken, formSubmissionRequest)
        }
    }

    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")  // Define key for token
    }
}