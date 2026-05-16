package com.example.yarti.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun DriverScreen(navController: NavHostController, vehicle: String) {

    var eta by remember { mutableStateOf(8) }
    var status by remember {
        mutableStateOf("Driver is moving toward your pickup location...")
    }

    val vehicleEmoji = when (vehicle) {
        "Bike" -> "🏍️"
        "Auto" -> "🛺"
        "Cab" -> "🚕"
        else -> "🛺"
    }

    LaunchedEffect(Unit) {
        while (eta > 0) {
            delay(3000)
            eta--
        }
        status = "Driver Arrived"
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Real-Time Ride Tracking",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Driver Name: Ravi Kumar")

            Spacer(modifier = Modifier.height(10.dp))

            Text("Vehicle: $vehicleEmoji $vehicle")

            Spacer(modifier = Modifier.height(10.dp))

            Text("Vehicle Number: KA 01 AB 1234")

            Spacer(modifier = Modifier.height(20.dp))

            if (eta > 0) {
                Text(
                    text = "ETA: $eta mins",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(status)

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    navController.navigate("home")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}