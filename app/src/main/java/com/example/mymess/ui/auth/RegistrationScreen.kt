package com.example.mymess.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*

@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("Resident") }
    
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { navController.popBackStack() },
                tint = CorporateNavy
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Create Account",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold, color = CorporateNavy)
            )
            Spacer(modifier = Modifier.height(32.dp))
            
            ModernInput(value = name, onValueChange = { name = it }, label = "Full Name")
            Spacer(modifier = Modifier.height(16.dp))
            ModernInput(value = email, onValueChange = { email = it }, label = "Email Address")
            Spacer(modifier = Modifier.height(16.dp))
            ModernInput(value = phone, onValueChange = { phone = it }, label = "Phone Number")
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("I am a:", style = MaterialTheme.typography.labelLarge, color = PrimaryText)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                listOf("Resident", "Owner").forEach { role ->
                    FilterChip(
                        selected = selectedRole == role,
                        onClick = { selectedRole = role },
                        label = { Text(role) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CorporateNavy,
                            selectedLabelColor = CorporateWhite
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            CorporateButton(
                text = "Register",
                onClick = { viewModel.register(name, email, phone, selectedRole) }
            )
            
            if (uiState is AuthUiState.Success) {
                // Navigate to Login or Dashboard directly?
                // For V3 Flow: Direct login
                val user = (uiState as AuthUiState.Success).user
                LaunchedEffect(Unit) {
                     if (user.role == com.example.mymess.domain.model.UserRole.OWNER) {
                        navController.navigate("owner_console") { popUpTo("register") { inclusive = true } }
                    } else {
                        navController.navigate("mess_feed") { popUpTo("register") { inclusive = true } }
                    }
                }
            }
        }
    }
}
