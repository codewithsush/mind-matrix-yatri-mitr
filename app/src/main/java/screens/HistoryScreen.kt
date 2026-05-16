package com.example.yarti.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class BookingItem(
    val pickup: String,
    val destination: String,
    val seats: String,
    val fare: String
)

@Composable
fun HistoryScreen(navController: NavHostController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser

    var bookings by remember { mutableStateOf(listOf<BookingItem>()) }

    LaunchedEffect(Unit) {
        user?.let {
            db.collection("bookings")
                .whereEqualTo("userEmail", it.email)
                .get()
                .addOnSuccessListener { result ->
                    val list = mutableListOf<BookingItem>()

                    for (doc in result) {
                        list.add(
                            BookingItem(
                                pickup = doc.getString("pickup") ?: "",
                                destination = doc.getString("destination") ?: "",
                                seats = doc.getString("seats") ?: "",
                                fare = doc.get("fare")?.toString() ?: "0"
                            )
                        )
                    }

                    bookings = list
                }
        }
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Text(
                "Booking History",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn {
                items(bookings) { booking ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Pickup: ${booking.pickup}")
                            Text("Destination: ${booking.destination}")
                            Text("Seats: ${booking.seats}")
                            Text("Fare: ₹${booking.fare}")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

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