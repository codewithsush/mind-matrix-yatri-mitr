package com.example.yarti.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PaymentScreen(navController: NavHostController) {

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current

    var selectedPayment by remember { mutableStateOf("UPI") }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Text(
                "Payments",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { selectedPayment = "UPI" },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("UPI")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { selectedPayment = "Card" },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Credit / Debit Card")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { selectedPayment = "Cash" },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cash")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    user?.let {
                        val paymentData = hashMapOf(
                            "userEmail" to it.email,
                            "paymentMethod" to selectedPayment
                        )

                        db.collection("payments")
                            .add(paymentData)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    "Payment Saved",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm Payment")
            }

            Spacer(modifier = Modifier.height(12.dp))

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