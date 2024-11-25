package com.example.romaferrariapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.example.romaferrariapp.ui.auth.LoginScreen
import com.example.romaferrariapp.ui.auth.RegisterScreen
import com.example.romaferrariapp.ui.auth.DashboardScreen
import com.example.romaferrariapp.ui.cars.CreateCarScreen
import com.example.romaferrariapp.ui.cars.EditCarScreen
import com.example.romaferrariapp.ui.cars.ListCarsScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    NavHost(navController, startDestination = if (currentUser != null) "dashboard" else "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToListCars = {
                    navController.navigate("listCars")
                }
            )
        }
        composable("listCars") {
            ListCarsScreen(
                onNavigateToDashboard = {
                    navController.navigate("dashboard") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToCreateCar = {
                    navController.navigate("createCar") {
                        popUpTo("createCar") { inclusive = true }
                    }
                },
                onNavigateToCarUpdate = { carId ->
                    navController.navigate("editCar/$carId")
                }
            )
        }
        composable("createCar") {
            CreateCarScreen(
                onNavigateToListCars = {
                    navController.navigate("listCars") {
                        popUpTo("listCars") { inclusive = true }
                    }
                }
            )
        }
        composable("editCar/{carId}") {
            EditCarScreen(
                onNavigateToListCars = {
                    navController.navigate("listCars") {
                        popUpTo("listCars") { inclusive = true }
                    }
                },
                carId = it.arguments?.getString("carId") ?: ""
            )
        }
    }
}