package com.example.mymess.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*
import com.example.mymess.ui.auth.AuthViewModel

@Composable
fun MessProfileScreen(
    navController: NavController,
    viewModel: MessProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("Sunshine Mess") }
    var address by remember { mutableStateOf("123, Anna Nagar") }
    var contact by remember { mutableStateOf("9876543210") }

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(title = "Mess Profile", onBack = { navController.popBackStack() })

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                 // Profile Header (Owner)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(CorporateNavy), contentAlignment = Alignment.Center) {
                        Text("O", style = MaterialTheme.typography.headlineLarge, color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Admin Owner", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = PrimaryText)
                        Text("Sunshine Mess", style = MaterialTheme.typography.bodyLarge, color = SecondaryText)
                    }
                }
                
                Divider(color = CorporateLightGray)

                CorporateCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Edit Details", style = MaterialTheme.typography.labelMedium, color = SecondaryText)
                        ModernInput(value = name, onValueChange = { name = it }, label = "Mess Name")
                        ModernInput(value = address, onValueChange = { address = it }, label = "Address")
                        ModernInput(value = contact, onValueChange = { contact = it }, label = "Contact Phone")
                    }
                }

                CorporateButton(
                    text = "Save Changes",
                    onClick = {
                        viewModel.updateMess(name, address, contact)
                        navController.popBackStack() 
                    }
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Logout Button
                Button(
                    onClick = { 
                        authViewModel.logout {
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
                ) {
                    Text("Logout", fontSize = MaterialTheme.typography.titleMedium.fontSize)
                }
            }
        }
    }
}
