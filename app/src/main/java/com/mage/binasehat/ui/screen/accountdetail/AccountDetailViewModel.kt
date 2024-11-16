package com.mage.binasehat.ui.screen.accountdetail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mage.binasehat.data.remote.response.DetailUserResponse
import com.mage.binasehat.data.remote.response.FormResponse
import com.mage.binasehat.data.remote.response.RegisterResponse
import com.mage.binasehat.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    private val userRepository: UserRepository // Repository or data source for fetching data
) : ViewModel() {

    private val _userDetailResponse = MutableStateFlow<DetailUserResponse?>(null)
    val userDetailResponse: StateFlow<DetailUserResponse?> = _userDetailResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    /// Fetch the user details when the screen is initialized
    fun fetchUserDetails() {
        viewModelScope.launch {
            val authToken = userRepository.getUserToken() // Get token from DataStore
            if (authToken != null) {
                _isLoading.value = true
                try {
                    val response = userRepository.getDetailUser(authToken)
                    _userDetailResponse.value = response
                } catch (e: Exception) {
                    // Handle error (e.g., network failure)
                    _userDetailResponse.value = null
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    // Handle photo upload
    fun uploadPhoto(photoUrl: MultipartBody.Part) {
        viewModelScope.launch {
            val authToken = userRepository.getUserToken()
            if (authToken != null) {
                try {
                    userRepository.upload(authToken, photoUrl) // Upload the photo URL to the API
                } catch (e: Exception) {
                    Log.e("AccountDetailViewModel", "Error uploading photo: ${e.message}")
                }
            }
        }
    }


}