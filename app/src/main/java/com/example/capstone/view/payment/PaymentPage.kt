package com.example.capstone.view.payment

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.capstone.R
import com.example.capstone.utils.DisableBackPress
import kotlinx.coroutines.delay

@Composable
fun PaymentPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    calendarLink: String? = null,
    bookingData: String? = null,
    viewModel: PaymentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    DisableBackPress()

    val timeRemaining by viewModel.timeRemaining.collectAsState()
    val showTimeoutDialog by viewModel.showTimeoutDialog.collectAsState()
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setSelectedImage(uri)
    }

    LaunchedEffect(showTimeoutDialog) {
        if (showTimeoutDialog) {
            delay(2000)
            viewModel.dismissTimeoutDialog()
            navController.navigate("MainPage/home") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    if (showTimeoutDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Transaksi Gagal") },
            text = { Text("Waktu pembayaran telah habis. Anda akan dikembalikan ke halaman utama.") },
            confirmButton = { }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sisa waktu pembayaran: ${String.format("%02d:%02d", timeRemaining / 60, timeRemaining % 60)}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = when {
                timeRemaining <= 300 -> Color.Red
                timeRemaining <= 600 -> Color(0xFFFF6B00)
                else -> Color.Black
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .background(Color(0xFFF9FAFB))
                .padding(16.dp)
        ) {
            Text(
                text = "Detail Pembayaran",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Total Pembayaran: Rp 300.000")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Transfer ke:")
            Text("Bank: BCA")
            Text("No. Rekening: 1234567890")
            Text("Atas Nama: PT Example")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .background(Color(0xFFF9FAFB))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Upload Bukti Pembayaran",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            selectedImageUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            OutlinedButton(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Pilih Bukti Pembayaran",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (selectedImageUri != null) {
                    val encodedLink = Uri.encode(calendarLink ?: "")
                    navController.navigate("payment_success/$encodedLink") {
                        popUpTo("payment") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006778)),
            enabled = selectedImageUri != null
        ) {
            Text(
                text = "Konfirmasi Pembayaran",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}