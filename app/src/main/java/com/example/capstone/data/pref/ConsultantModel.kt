package com.example.capstone.data.pref

data class ConsultantData(
    val id: String,
    val name: String,
    val photoUrl: String,
    val calendarLink: String,
    val expertises: List<String>,
    val availableSlots: List<TimeSlot> = emptyList()
)

data class TimeSlot(
    val date: String, // format: "dd MMMM yyyy"
    val availableHours: List<String> // format: "HH:mm"
)

data class ConsultantSchedule(
    val date: String,
    val time: String,
    val consultantName: String,
    val consultantPhoto: String // Tambahkan properti ini
)

//data class ConsultantSchedule(
//    val id: String,
//    val date: String,
//    val time: String,
//    val consultantName: String,
//    val consultantPhoto: String,
//    val status: String = "upcoming" // upcoming, completed, cancelled
//)
