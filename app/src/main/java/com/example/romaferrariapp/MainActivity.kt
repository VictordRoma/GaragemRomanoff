package com.example.romaferrariapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.romaferrariapp.ui.theme.RomaFerrariAppTheme
import com.example.romaferrariapp.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RomaFerrariAppTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}