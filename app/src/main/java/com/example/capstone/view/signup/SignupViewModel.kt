package com.example.capstone.view.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.UserRepository
import com.example.capstone.data.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: UserRepository) : ViewModel() {
    private val _registerResult = MutableStateFlow<Result<String>>(Result.Initial)
    val registerResult: StateFlow<Result<String>> = _registerResult.asStateFlow()

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.register(name, email, password).collect { result ->
                _registerResult.value = result
            }
        }
    }
}