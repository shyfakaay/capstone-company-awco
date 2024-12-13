package com.example.capstone.view.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.capstone.R
import com.example.capstone.ViewModelFactory
import com.example.capstone.di.Injection
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SplashViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val isLogin by viewModel.isLogin.collectAsState()

    LaunchedEffect(key1 = true) {
        delay(1500L) // Delay 1.5 detik
        if (!isLoading) {
            if (isLogin) {
                navController.navigate("MainPage") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo1),
                contentDescription = "Logo Splash",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Consult App",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0099A9)
            )
        }
    }
}

// Hapus Preview karena bisa menyebabkan masalah dengan dependency injection


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    // Anda dapat menghapus parameter `navController` dalam preview karena tidak diperlukan
    SplashScreen(navController = NavController(context = androidx.compose.ui.platform.LocalContext.current))
}