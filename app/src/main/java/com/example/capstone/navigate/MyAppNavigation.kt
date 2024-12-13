package com.example.capstone.navigate

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.capstone.view.login.LoginPage
import com.example.capstone.view.main.MainPage
import com.example.capstone.view.payment.PaymentPage
import com.example.capstone.view.payment.PaymentSucces
import com.example.capstone.view.signup.SignupPage
import com.example.capstone.view.splash.SplashScreen

@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController = navController)
        }

        composable("login") {
            LoginPage(modifier = modifier, navController = navController)
        }

        composable("signup") {
            SignupPage(modifier = modifier, navController = navController)
        }

        composable("MainPage") {
            MainPage(modifier = modifier, navController = navController)
        }

        composable("MainPage/profile") {
            MainPage(
                modifier = modifier,
                navController = navController,
                startDestination = "profile"
            )
        }

        composable("MainPage/home") {
            MainPage(
                modifier = modifier,
                navController = navController,
                startDestination = "home"
            )
        }

        composable(
            route = "payment/{calendarLink}/{bookingData}",
            arguments = listOf(
                navArgument("calendarLink") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("bookingData") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val link = backStackEntry.arguments?.getString("calendarLink")
            val data = backStackEntry.arguments?.getString("bookingData")

            PaymentPage(
                modifier = modifier,
                navController = navController,
                calendarLink = link?.let { Uri.decode(it) },
                bookingData = data?.let { Uri.decode(it) }
            )
        }

        composable(
            route = "payment_success/{calendarLink}",
            arguments = listOf(
                navArgument("calendarLink") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val link = backStackEntry.arguments?.getString("calendarLink")

            PaymentSucces(
                navController = navController,
                calendarLink = link?.let { Uri.decode(it) }
            )
        }
    }
}