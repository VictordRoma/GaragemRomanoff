package com.example.romaferrariapp.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val primary = Color(0xFFC7FF00)
val primaryContainer = Color(0xFF3B3B3B)
val secondaryContainer = Color(0xFF444444)
val background = Color(0xFFFF0000)

val CustomColorScheme = lightColorScheme(
    primary = secondaryContainer,
    onPrimary = primary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = primary,
    secondaryContainer = secondaryContainer,
    onSecondaryContainer = primary,
    secondary = primary,
    onSecondary = secondaryContainer,
    error = Color.White,
    onError = Color.White,
    errorContainer = Color.Red,
    background = background,
)