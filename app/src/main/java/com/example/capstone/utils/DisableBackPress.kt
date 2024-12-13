package com.example.capstone.utils

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun DisableBackPress() {
    BackHandler(enabled = true) {
        // Tidak melakukan apa-apa ketika tombol back ditekan
    }
}