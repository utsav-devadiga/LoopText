package com.playlabs.auth.ui

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.playlabs.auth.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val phoneNumber = remember { mutableStateOf("") }
    val otp = remember { mutableStateOf("") }
    val loginState by viewModel.loginState.observeAsState()

    val activity = LocalContext.current as? Activity

    Column {
        // Phone Number Input
        TextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Mobile Number") }
        )
        Button(onClick = {
            viewModel.sendOtp("+91${phoneNumber.value}", activity = activity)
        }) {
            Text("Send OTP")
        }

        // OTP Input
        TextField(
            value = otp.value,
            onValueChange = { otp.value = it },
            label = { Text("OTP") }
        )
        Button(onClick = { viewModel.verifyOtp(otp.value) }) {
            Text("Verify OTP")
        }

        // Show Login Result
        if (loginState == true) {
            Log.d("AUTH", "LoginScreen: $loginState ")
            onLoginSuccess()
        }
    }
}
