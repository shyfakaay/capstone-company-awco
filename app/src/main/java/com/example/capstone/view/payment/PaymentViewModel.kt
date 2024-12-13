package com.example.capstone.view.payment

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {
    private val _timeRemaining = MutableStateFlow(3600L) // 1 jam dalam detik 3600
    val timeRemaining: StateFlow<Long> = _timeRemaining

    private val _showTimeoutDialog = MutableStateFlow(false)
    val showTimeoutDialog: StateFlow<Boolean> = _showTimeoutDialog

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    private var countdownJob: Job? = null

    init {
        startCountdown()
    }

    private fun startCountdown() {
        countdownJob = viewModelScope.launch {
            while (_timeRemaining.value > 0) {
                delay(1000)
                _timeRemaining.value -= 1

                // Tampilkan peringatan saat waktu hampir habis (opsional)
                when (_timeRemaining.value) {
                    300L -> { // 5 menit tersisa
                        // Implementasi peringatan jika diperlukan
                    }
                    60L -> { // 1 menit tersisa
                        // Implementasi peringatan jika diperlukan
                    }
                }
            }
            // Waktu habis
            _showTimeoutDialog.value = true
        }
    }

    fun setSelectedImage(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun dismissTimeoutDialog() {
        _showTimeoutDialog.value = false
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }
}