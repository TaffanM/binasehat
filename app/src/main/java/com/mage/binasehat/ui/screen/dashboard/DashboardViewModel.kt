package com.mage.binasehat.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mage.binasehat.data.remote.response.DetailUserResponse
import com.mage.binasehat.data.remote.response.FormResponse
import com.mage.binasehat.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // StateFlow to hold the user details response
    private val _userDetailResponse = MutableStateFlow<DetailUserResponse?>(null)
    val userDetailResponse: StateFlow<DetailUserResponse?> = _userDetailResponse

    // StateFlow to manage the loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _formResponse = MutableStateFlow<FormResponse?>(null)
    val formResponse: StateFlow<FormResponse?> = _formResponse

    private var originalUserDetail: DetailUserResponse? = null

    // Fetch the user details when the screen is initialized
    fun fetchUserDetails() {
        viewModelScope.launch {
            val authToken = userRepository.getUserToken() // Get token from DataStore
            if (authToken != null) {
                _isLoading.value = true
                try {
                    val response = userRepository.getDetailUser(authToken)
                    originalUserDetail = response
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

//    // Fetch the FormResponse data
//    fun fetchFormResponse() {
//        viewModelScope.launch {
//            val authToken = userRepository.getUserToken() // Get token from DataStore
//            if (authToken != null) {
//                _isLoading.value = true
//                try {
//                    val response = userRepository.getFormResponse(authToken) // Fetch FormResponse from repository
//                    _formResponse.value = response
//                } catch (e: Exception) {
//                    // Handle error (e.g., network failure)
//                    _formResponse.value = null
//                } finally {
//                    _isLoading.value = false
//                }
//            }
//        }
//    }

    fun filterDailyCaloriesByDate(date: String) {
        val filteredResponse = originalUserDetail?.let {
            if (it.userDetail.lastCaloriesUpdateDate == date) it else null
        }
        _userDetailResponse.value = filteredResponse
    }
}