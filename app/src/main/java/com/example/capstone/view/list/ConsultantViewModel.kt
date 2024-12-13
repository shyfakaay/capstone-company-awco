package com.example.capstone.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.pref.ConsultantData
import com.example.capstone.data.pref.TimeSlot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConsultantViewModel : ViewModel() {
    private val _consultants = MutableStateFlow<List<ConsultantData>>(emptyList())
    val consultants: StateFlow<List<ConsultantData>> = _consultants.asStateFlow()

    private val _selectedConsultant = MutableStateFlow<ConsultantData?>(null)
    val selectedConsultant: StateFlow<ConsultantData?> = _selectedConsultant.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> = _showDatePicker.asStateFlow()

    private val _storyText = MutableStateFlow("")
    val storyText: StateFlow<String> = _storyText.asStateFlow()

    init {
        fetchConsultants()
    }

    private fun fetchConsultants() {
        viewModelScope.launch {
            // Simulasi data API dengan dummy
            _consultants.value = listOf(
                ConsultantData(
                    id = "1",
                    name = "Ardhi Widjaya",
                    photoUrl = "ardhi_widjaya",
                    calendarLink = "https://calendar.google.com/calendar/u/0/appointments/schedules/AcZssZ3ou7hTumg2a46b9WxaVqf36gJRayY0EAPt687YeknZ6k3l-cWsfns5t5JQTdkvUhyWc6D-OcSw",
                    expertises = listOf(
                        "Marketing Communication",
                        "HR Management",
                        "Sales",
                        "Business Development"
                    ),
                    availableSlots = listOf(
                        TimeSlot(
                            date = "Senin",
                            availableHours = listOf("09.00-10.30")
                        ),
                        TimeSlot(
                            date = "Rabu",
                            availableHours = listOf("09.00-10.30")
                        ),
                        TimeSlot(
                            date = "Kamis",
                            availableHours = listOf("09.00-10.30")
                        ),
                        TimeSlot(
                            date = "Jumat",
                            availableHours = listOf("09.00-10.30")
                        )
                    )
                ),
                ConsultantData(
                    id = "2",
                    name = "M. Reza Maulana",
                    photoUrl = "reza_maulana",
                    calendarLink = "https://calendar.google.com/calendar/u/0/appointments/schedules/AcZssZ2ptt7YILCltgJRtnZTLW4UlRIJfOV9-alLyOD-mmW0FF8GdFUxmuWu1mXTP4qHKp-e-AqOOgBO",
                    expertises = listOf(
                        "Project Management",
                        "Operational Management at IT/ Start Up Industries",
                    ),
                    availableSlots = listOf(
                        TimeSlot(
                            date = "Selasa",
                            availableHours = listOf("09.00-10.30")
                        )
                    )
                ),

            )
        }
    }

    fun selectConsultant(consultant: ConsultantData) {
        _selectedConsultant.value = consultant
        _showDatePicker.value = true
    }

    fun dismissDatePicker() {
        _showDatePicker.value = false
        _selectedConsultant.value = null
    }

    fun updateStoryText(text: String) {
        _storyText.value = text
    }

    fun sendStory(story: String) {
        viewModelScope.launch {
            // Implementasi pengiriman cerita ke API
            _storyText.value = ""
        }
    }
}
