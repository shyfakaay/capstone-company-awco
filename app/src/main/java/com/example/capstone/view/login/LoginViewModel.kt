package com.example.capstone.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.UserRepository
import com.example.capstone.data.pref.UserModel
import com.example.capstone.data.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableStateFlow<Result<UserModel>>(Result.Initial)
    val loginResult: StateFlow<Result<UserModel>> = _loginResult.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).collect { result ->
                _loginResult.value = when (result) {
                    is Result.Success -> {
                        // Copy data user dan set isLogin menjadi true
                        val user = result.data.copy(isLogin = true)

                        // Simpan session ke UserPreference
                        repository.saveSession(user)

                        Result.Success(user)
                    }

                    else -> {
                        result
                    }
                }
            }
        }
    }
}