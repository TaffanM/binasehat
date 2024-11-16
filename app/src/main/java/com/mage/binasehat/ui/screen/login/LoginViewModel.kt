package com.mage.binasehat.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mage.binasehat.data.remote.model.LoginRequest
import com.mage.binasehat.data.remote.response.LoginResponse
import com.mage.binasehat.data.util.Result
import com.mage.binasehat.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _loginResult = MutableStateFlow<Result<LoginResponse>>(Result.Idle)
    val loginResult: MutableStateFlow<Result<LoginResponse>> get() =  _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Result.Loading
            try {
                val loginRequest = LoginRequest(email, password)
                val response = userRepository.login(loginRequest)
                _loginResult.value = Result.Success(response)
            } catch (e: Exception) {
                _loginResult.value = Result.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.clearUserData() // Clear saved user data, such as tokens
            _loginResult.value = Result.Idle // Reset login state
        }
    }


}