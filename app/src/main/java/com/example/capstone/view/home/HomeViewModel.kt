package com.example.capstone.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _selectedDate = MutableStateFlow<String?>(null)
    val selectedDate: StateFlow<String?> = _selectedDate.asStateFlow()

    private val _selectedTime = MutableStateFlow<String?>(null)
    val selectedTime: StateFlow<String?> = _selectedTime.asStateFlow()

    fun openGoogleCalendar() {
        viewModelScope.launch {
            try {
                // Implementasi Google Calendar API
                // 1. Buka Google Calendar
                // 2. Dapatkan tanggal dan waktu yang dipilih
                // 3. Update state
                _selectedDate.value = "Tanggal yang dipilih"
                _selectedTime.value = "Waktu yang dipilih"
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}