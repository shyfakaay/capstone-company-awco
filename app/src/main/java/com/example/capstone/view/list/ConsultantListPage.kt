package com.example.capstone.view.list

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.capstone.R
import com.example.capstone.data.pref.ConsultantData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capstone.utils.DisableBackPress

@Composable
fun BookingDialog(
    consultant: ConsultantData,
    onDismiss: () -> Unit,
    onConfirm: (date: String, time: String, calendarLink: String) -> Unit
) {
    var selectedDate by remember { mutableStateOf<String?>(null) }
    var selectedTime by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Book Session with ${consultant.name}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    "Hari yang tersedia:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                consultant.availableSlots.forEach { slot ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                selectedDate = slot.date
                                selectedTime = slot.availableHours.firstOrNull()
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedDate == slot.date)
                                Color(0xFFE6F3F5) else Color.White
                        )
                    ) {
                        Text(
                            text = "${slot.date} (${slot.availableHours.first()})",
                            modifier = Modifier.padding(16.dp),
                            color = if (selectedDate == slot.date)
                                Color(0xFF006778) else Color.Black,
                            fontWeight = if (selectedDate == slot.date)
                                FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedDate != null && selectedTime != null) {
                        onConfirm(selectedDate!!, selectedTime!!, consultant.calendarLink)
                    }
                },
                enabled = selectedDate != null && selectedTime != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF006778),
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text("Book Now")
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
fun ConsultListPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ConsultantViewModel = viewModel()
) {

    DisableBackPress()

    val consultants by viewModel.consultants.collectAsState()
    val selectedConsultant by viewModel.selectedConsultant.collectAsState()
    val showDatePicker by viewModel.showDatePicker.collectAsState()
    val storyText by viewModel.storyText.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Select And Booking Your\nConsultant",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    StoryCard(
                        storyText = storyText,
                        onStoryChange = { viewModel.updateStoryText(it) },
                        onSendStory = { viewModel.sendStory(storyText) }
                    )
                }

                items(consultants) { consultant ->
                    ConsultantCard(
                        consultant = consultant,
                        onBookingClick = { viewModel.selectConsultant(consultant) }
                    )
                }
            }
        }

        // Tampilkan BookingDialog ketika consultant dipilih
        selectedConsultant?.let { consultant ->
            if (showDatePicker) {
                BookingDialog(
                    consultant = consultant,
                    onDismiss = { viewModel.dismissDatePicker() },
                    onConfirm = { date, time, calendarLink ->
                        val encodedLink = Uri.encode(calendarLink)
                        val encodedBookingData = Uri.encode("$date $time")
                        navController.navigate("payment/$encodedLink/$encodedBookingData")
                        viewModel.dismissDatePicker()
                    }
                )
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

@Composable
fun StoryCard(
    storyText: String,
    onStoryChange: (String) -> Unit,
    onSendStory: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "Chat Icon",
                    tint = Color(0xFF006778),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ceritakan Keluh Kesah Mu",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF006778)
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Kami akan merekomendasikan konsultan berdasarkan cerita mu",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = storyText,
                onValueChange = onStoryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                placeholder = { Text("Tulis ceritamu di sini...") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFF006778),
                    focusedBorderColor = Color(0xFF006778)
                ),
                trailingIcon = {
                    IconButton(
                        onClick = onSendStory,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color(0xFF006778)
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun ConsultantCard(
    consultant: ConsultantData,
    onBookingClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onBookingClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Menggunakan Image dari resource drawable
            val imageRes = when (consultant.photoUrl) {
                "ardhi_widjaya" -> R.drawable.ardhi_widjaya
                "reza_maulana" -> R.drawable.reza_maulana
                else -> R.drawable.ic_profile_placeholder
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Consultant Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = consultant.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Expertise:",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )

            consultant.expertises.forEachIndexed { index, expertise ->
                Text(
                    text = "${index + 1}. $expertise",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onBookingClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006778))
            ) {
                Text(
                    "Booking Now",
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConsultListPagePreview() {
    ConsultListPage(
        navController = NavController(context = androidx.compose.ui.platform.LocalContext.current)
    )
}