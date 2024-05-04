package com.example.myapplication.ui.screens.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fredy.roomdatabase3.ui.authentication.CustomTextField

@Composable
fun ForgotPassword(gotoSignIn: () -> Unit) {
    val email = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Forgot Password",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            CustomTextField(
                label = "Email",
                value = email.value,
                onValueChange = { email.value = it },
                placeholder = "typeEmail...",
                keyboardType = KeyboardType.Email
            )
            Button(
                onClick = {
                    // Handle forgot password action here
                    // For example, you can show a success message and navigate back to the sign-in screen
                    gotoSignIn()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(
                    text = "Reset Password",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Remember your password? Sign in",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable(onClick = {
                    gotoSignIn()
                })
            )
        }
    }
}
