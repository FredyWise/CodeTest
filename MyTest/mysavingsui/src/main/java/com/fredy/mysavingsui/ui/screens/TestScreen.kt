package com.fredy.mysavingsui.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TestScreen(name: String) {
    Text(text = name, style = MaterialTheme.typography.headlineLarge)
}