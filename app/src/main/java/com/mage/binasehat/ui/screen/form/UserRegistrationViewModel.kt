package com.mage.binasehat.ui.screen.form

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mage.binasehat.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mage.binasehat.data.util.Result

@HiltViewModel
class UserRegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // StateFlow for registration data
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    // Function to update the registration data
    fun updateUsername(value: String) {
        _username.value = value
    }

    fun updateEmail(value: String) {
        _email.value = value
    }

    fun updatePassword(value: String) {
        _password.value = value
    }

    fun updateConfirmPassword(value: String) {
        _confirmPassword.value = value
    }

    // StateFlow for form submission
    private val _formSubmissionState = MutableStateFlow<Result<Unit>>(Result.Idle)
    val formSubmissionState: StateFlow<Result<Unit>> = _formSubmissionState

    // Submit form including body stats
    fun submitForm(birt: String, gender: String, tall: Float, weigh: Float) {
        viewModelScope.launch {
            _formSubmissionState.value = Result.Loading
            Log.d("UserRegistrationViewModel", "birt: $birt, gender: $gender, tall: $tall, weigh: $weigh")
            Log.d("UserRegistrationViewModel", "username: ${_username.value}, email: ${_email.value}, password: ${_password.value}, confirmPassword: ${_confirmPassword.value}")

            try {
                // Step 1: Register the user and get the auth token
                val registerResponse = userRepository.register(_username.value, _email.value, _password.value)

                // Step 2: Use the auth token to submit the form
                val authToken = "Bearer ${registerResponse.token}"
                userRepository.submitForm(authToken, birt, gender, tall, weigh)

                // Step 3: Update the state to success
                _formSubmissionState.value = Result.Success(Unit)

            } catch (e: Exception) {
                _formSubmissionState.value = Result.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
