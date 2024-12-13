package com.example.capstone.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.capstone.utils.DisableBackPress
import com.example.capstone.view.home.HomePage
import com.example.capstone.view.list.ConsultListPage
import com.example.capstone.view.profile.ProfilePage

@Composable
fun MainPage(
    modifier: Modifier,
    navController: NavController,
    startDestination: String = "home"
) {
    DisableBackPress()
    var selectedItem by remember { mutableStateOf(startDestination) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedItem == "home",
                    onClick = { selectedItem = "home" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = "Consultant") },
                    label = { Text("Consultant") },
                    selected = selectedItem == "consultant",
                    onClick = { selectedItem = "consultant" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedItem == "profile",
                    onClick = { selectedItem = "profile" }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedItem) {
                "home" -> HomePage(navController = navController)
                "consultant" -> ConsultListPage(navController = navController)
                "profile" -> ProfilePage(navController = navController)
            }
        }
    }
}