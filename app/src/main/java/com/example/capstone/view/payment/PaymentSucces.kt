package com.example.capstone.view.payment

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.capstone.R
import com.example.capstone.utils.DisableBackPress

@Composable
fun PaymentSucces(
    modifier: Modifier = Modifier,
    navController: NavController,
    calendarLink: String?
) {
    DisableBackPress()

    var showCalendarDialog by remember { mutableStateOf(false) }
    var isScheduleConfirmed by remember { mutableStateOf(false) }
    var hasCompletedBooking by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_success),
            contentDescription = "Success Icon",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Pembayaran Berhasil!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF006778)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Silakan konfirmasi jadwal booking Anda",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { showCalendarDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006778)),
            enabled = !hasCompletedBooking
        ) {
            Text(
                text = if (hasCompletedBooking) "Jadwal Telah Dikonfirmasi" else "Konfirmasi Jadwal",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("MainPage/profile") {
                    popUpTo(0)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = hasCompletedBooking,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF006778),
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(
                text = "Lihat Profile",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }

    if (showCalendarDialog) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Silakan selesaikan booking Anda",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = { showCalendarDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text("Tutup", color = Color.White)
                        }
                    }

                    AndroidView(
                        factory = { context ->
                            WebView(context).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                settings.javaScriptEnabled = true
                                settings.domStorageEnabled = true
                                settings.loadWithOverviewMode = true
                                settings.useWideViewPort = true

                                webViewClient = object : WebViewClient() {
                                    override fun onPageFinished(view: WebView?, url: String?) {
                                        super.onPageFinished(view, url)
                                        view?.evaluateJavascript(
                                            "(document.body.innerText.includes('Pemesanan dikonfirmasi') || " +
                                                    "document.body.innerText.includes('Email dikirim') || " +
                                                    "document.body.innerText.includes('Booking confirmed'))"
                                        ) { result ->
                                            if (result == "true") {
                                                isScheduleConfirmed = true
                                                showCalendarDialog = false
                                                hasCompletedBooking = true
                                            }
                                        }
                                    }
                                }

                                loadUrl(calendarLink ?: "https://calendar.google.com/calendar/default")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        }
    }
}