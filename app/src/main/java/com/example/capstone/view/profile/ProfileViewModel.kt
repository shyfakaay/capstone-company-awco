package com.example.capstone.view.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.UserRepository
import com.example.capstone.data.pref.ConsultantSchedule
import com.example.capstone.data.pref.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _userProfile = MutableStateFlow<UserModel?>(null)
    val userProfile: StateFlow<UserModel?> = _userProfile

    private val _schedules = MutableStateFlow<List<ConsultantSchedule>>(emptyList())
    val schedules: StateFlow<List<ConsultantSchedule>> = _schedules

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut: StateFlow<Boolean> = _isLoggedOut

    init {
        getUserProfile()
//        getSchedules()
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            repository.getSession().collect { user ->
                _userProfile.value = user
            }
        }
    }

    fun addSchedule(schedule: ConsultantSchedule) {
        val currentSchedules = _schedules.value.toMutableList()
        currentSchedules.add(schedule)
        _schedules.value = currentSchedules
    }

//dummy
//    private fun getSchedules() {
//        _schedules.value = listOf(
//            ConsultantSchedule(
//                id = "1",
//                date = "2024-03-20",
//                time = "10:00",
//                consultantName = "Dr. John Doe",
//                consultantPhoto = "https://example.com/photo1.jpg"
//            ),
//            ConsultantSchedule(
//                id = "2",
//                date = "2024-03-22",
//                time = "14:00",
//                consultantName = "Dr. Jane Smith",
//                consultantPhoto = "https://example.com/photo2.jpg"
//            )
//        )
//    }

    fun updateProfileImage(imageUri: String) {
        viewModelScope.launch {
            _userProfile.value?.let { currentUser ->
                val updatedUser = currentUser.copy(photoUrl = imageUri)
                repository.updateUserProfile(updatedUser)
                _userProfile.value = updatedUser
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                repository.logout()
                _isLoggedOut.value = true
            } catch (e: Exception) {
                // Handle error jika perlu
                e.printStackTrace()
            }
        }
    }
}