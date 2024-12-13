package com.example.capstone.view.home

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capstone.utils.DisableBackPress
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch

@Composable
fun CustomTimePickerDialog(
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit
) {
    DisableBackPress()

    val hours = (7..20).toList()
    val minutes = (0..55 step 5).toList()

    var selectedHour by remember { mutableStateOf(8) }
    var selectedMinute by remember { mutableStateOf(0) }

    val hourState = rememberLazyListState()
    val minuteState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Select Time", style = MaterialTheme.typography.titleLarge) },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Hours
                Box(
                    modifier = Modifier.width(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        state = hourState,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(hours.size) { index ->
                            Text(
                                text = String.format("%02d", hours[index]),
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        selectedHour = hours[index]
                                    },
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = if (hours[index] == selectedHour) Color(0xFF006778) else Color.Black,
                                    fontWeight = if (hours[index] == selectedHour) FontWeight.Bold else FontWeight.Normal
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Text(":", style = MaterialTheme.typography.headlineMedium)

                // Minutes
                Box(
                    modifier = Modifier.width(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        state = minuteState,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(minutes.size) { index ->
                            Text(
                                text = String.format("%02d", minutes[index]),
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        selectedMinute = minutes[index]
                                    },
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = if (minutes[index] == selectedMinute) Color(0xFF006778) else Color.Black,
                                    fontWeight = if (minutes[index] == selectedMinute) FontWeight.Bold else FontWeight.Normal
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(String.format("%02d:%02d", selectedHour, selectedMinute))
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val currentDate = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("MMMM yyyy", Locale("id"))
    val currentMonthYear = dateFormat.format(currentDate.time)
    var expanded by remember { mutableStateOf(false) }
    var selectedSessionType by remember { mutableStateOf("Select Expertise") }
    var duration by remember { mutableStateOf("-") }
    var dateTime by remember { mutableStateOf("-") }
    var showCustomTimePicker by remember { mutableStateOf(false) }
    val context = androidx.compose.ui.platform.LocalContext.current
    val calendar = Calendar.getInstance()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val sessionTypes = listOf(
        "Marketing Communication",
        "Human Resource Management",
        "Sales",
        "Business Development",
        "Project Management",
        "Operational Management IT & Industries",
        "Public Relation",
        "Small Business Management",
        "Service Excellence Tourism Industries",
        "Social Media Content",
        "Digital Marketing Startup & Agencies",
        "Operational & Marketing Management F&B Or Culinary Industries"
    )

    // Tampilkan Custom Time Picker
    if (showCustomTimePicker) {
        CustomTimePickerDialog(
            onDismiss = { showCustomTimePicker = false },
            onTimeSelected = { time ->
                duration = time
            }
        )
    }

    // Fungsi untuk menampilkan Date Picker yang kompatibel dengan API 21
    fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id"))
                dateTime = dateFormat.format(calendar.time)
                // Tampilkan custom time picker setelah memilih tanggal
                showCustomTimePicker = true
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Select Date & Time",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = currentMonthYear,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT").forEach { day ->
                                    Text(
                                        text = day,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = { showDatePickerDialog() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006778))
                            ) {
                                Text("Pick Date & Time")
                            }
                        }
                    }
                }

                // ... rest of the code remains the same ...
                // (Card dengan Session Detail, form fields, dan tombol Booking Session)

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Session Detail",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Text("Session Type", modifier = Modifier.padding(bottom = 4.dp))
                            Box {
                                OutlinedTextField(
                                    value = selectedSessionType,
                                    onValueChange = { },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { expanded = true },
                                    enabled = false,
                                    trailingIcon = {
                                        Icon(
                                            if (expanded) Icons.Default.KeyboardArrowUp
                                            else Icons.Default.KeyboardArrowDown,
                                            "dropdown arrow",
                                            Modifier.clickable { expanded = !expanded }
                                        )
                                    },
                                    colors = TextFieldDefaults.colors(
                                        disabledContainerColor = Color.Transparent,
                                        disabledTextColor = Color.Black,
                                        disabledIndicatorColor = Color.Gray
                                    )
                                )

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .background(Color.White)
                                ) {
                                    sessionTypes.forEach { session ->
                                        DropdownMenuItem(
                                            text = { Text(text = session) },
                                            onClick = {
                                                selectedSessionType = session
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Duration", modifier = Modifier.padding(bottom = 4.dp))
                            OutlinedTextField(
                                value = duration,
                                onValueChange = { },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = false,
                                colors = TextFieldDefaults.colors(
                                    disabledContainerColor = Color.Transparent,
                                    disabledTextColor = Color.Black,
                                    disabledIndicatorColor = Color.Gray
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Date & Time", modifier = Modifier.padding(bottom = 4.dp))
                            OutlinedTextField(
                                value = dateTime,
                                onValueChange = { },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = false,
                                colors = TextFieldDefaults.colors(
                                    disabledContainerColor = Color.Transparent,
                                    disabledTextColor = Color.Black,
                                    disabledIndicatorColor = Color.Gray
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Fitur ini belum tersedia",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006778))
                            ) {
                                Text("Booking Session")
                            }
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePagePreview() {
    HomePage(navController = NavController(context = androidx.compose.ui.platform.LocalContext.current))
}