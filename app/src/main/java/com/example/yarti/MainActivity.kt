package com.example.yarti

import android.app.Activity
import android.location.Geocoder
import java.util.Locale
import kotlinx.coroutines.delay
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.example.yarti.screens.DriverScreen
import com.example.yarti.screens.HistoryScreen
import com.example.yarti.screens.LoginScreen
import com.example.yarti.screens.PaymentScreen
import com.example.yarti.screens.SignupScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YatriApp()
        }
    }
}

@Composable
fun YatriApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("booking") { BookingScreen(navController) }
        composable("profile") { ProfileScreen(navController) }

        composable("driver/{vehicle}") { backStackEntry ->
            val vehicle = backStackEntry.arguments?.getString("vehicle") ?: "Auto"
            DriverScreen(navController, vehicle)
        }

        composable("history") { HistoryScreen(navController) }
        composable("payment") { PaymentScreen(navController) }
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {

    val auth = FirebaseAuth.getInstance()

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)

        if (auth.currentUser != null) {
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
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
                "Yatri-Mitra",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text("Yatri-Mitra Dashboard", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate("booking") },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Book Ride") }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("driver") },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Driver Tracking") }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("history") },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Booking History") }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("payment") },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Payments") }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("profile") },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Profile") }
        }
    }
}

@Composable
fun BookingScreen(navController: NavHostController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var pickup by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var seats by remember { mutableStateOf("") }
    var selectedVehicle by remember { mutableStateOf("Bike") }

    val baseFare = when (selectedVehicle) {
        "Bike" -> 40
        "Auto" -> 80
        "Cab" -> 150
        else -> 40
    }

    val fare = (seats.toIntOrNull() ?: 0) * baseFare

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Text(
                text = "Ride Booking",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = pickup,
                onValueChange = { pickup = it },
                label = { Text("Pickup Location") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            context as Activity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            100
                        )
                        return@Button
                    }

                    fusedLocationClient.getCurrentLocation(
                        com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                        null
                    ).addOnSuccessListener { location ->

                        if (location != null) {

                            val geocoder = Geocoder(context, Locale.getDefault())
                            val addresses = geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            )

                            if (!addresses.isNullOrEmpty()) {
                                pickup = addresses[0].getAddressLine(0)
                            } else {
                                pickup = "Current Location Found"
                            }

                        } else {
                            Toast.makeText(
                                context,
                                "Unable to get current location",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("📍 Use Current Location")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = destination,
                onValueChange = { destination = it },
                label = { Text("Destination") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = seats,
                onValueChange = { seats = it },
                label = { Text("Number of Seats") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Select Vehicle",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { selectedVehicle = "Bike" }) {
                    Text("🏍️ Bike")
                }

                Button(onClick = { selectedVehicle = "Auto" }) {
                    Text("🛺 Auto")
                }

                Button(onClick = { selectedVehicle = "Cab" }) {
                    Text("🚕 Cab")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Estimated Fare: ₹$fare",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (pickup.isBlank() || destination.isBlank() || seats.isBlank()) {
                        Toast.makeText(
                            context,
                            "Fill all fields",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    user?.let {
                        val bookingData = hashMapOf(
                            "pickup" to pickup,
                            "destination" to destination,
                            "seats" to seats,
                            "vehicle" to selectedVehicle,
                            "fare" to fare,
                            "userEmail" to it.email
                        )

                        db.collection("bookings")
                            .add(bookingData)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    "Booking Confirmed",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.navigate("driver/$selectedVehicle")
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "Booking Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Book Ride")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate("home")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Return Home")
            }
        }
    }
}
@Composable
fun ProfileScreen(navController: NavHostController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        user?.let {
            db.collection("users").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        name = document.getString("name") ?: ""
                        phone = document.getString("phone") ?: ""
                    }
                }
        }
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
                text = "User Profile",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = user?.email ?: "",
                onValueChange = {},
                enabled = false,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    user?.let {
                        val profileData = hashMapOf(
                            "name" to name,
                            "phone" to phone,
                            "email" to it.email
                        )

                        db.collection("users").document(it.uid)
                            .set(profileData)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    "Profile Saved",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "Save Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Profile")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    auth.signOut()
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }

            Spacer(modifier = Modifier.height(10.dp))

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