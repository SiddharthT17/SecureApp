package com.dummyapp.sharesecureapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 6. LoginViewModel to Manage Login Logic and State
class LoginViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    val _isSuccess  = MutableStateFlow(false)
    var isSuccess = _isSuccess.asStateFlow()
    var errorMessage by mutableStateOf<String?>(null)

    // Simulate login process
    fun login(onSuccess: () -> Unit) {
        if (username.isBlank() || password.isBlank()) {
            errorMessage = "Please fill in both fields"
            return
        }

        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            isLoading = false

            if (username == "user" && password == "password") {
                _isSuccess.value = true
                onSuccess()
            } else {
                errorMessage = "Invalid username or password"
            }
        }
    }
}