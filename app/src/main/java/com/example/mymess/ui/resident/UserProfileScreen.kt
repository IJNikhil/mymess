package com.example.mymess.ui.resident

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
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
fun UserProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val resident by viewModel.resident.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<android.net.Uri?>(null) }
    
    // Image Picker Launcher
    val launcher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        selectedImageUri = uri
    }

    // Sync State
    LaunchedEffect(resident) {
        if (resident != null) {
            name = resident!!.name
            phone = resident!!.phoneNumber
            if (resident!!.idProofUrl != null) {
                // If it's a string from DB, we can't easily make it Uri unless parsable.
                // For MVP display, we handle string specially.
            }
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is ProfileUiState.Success) {
            isEditing = false
        }
    }
    
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        if (uiState is ProfileUiState.Loading) {
             CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(
                title = if(isEditing) "Edit Profile" else "My Profile", 
                onBack = { if(isEditing) isEditing = false else navController.popBackStack() },
                action = {
                    TextButton(onClick = { 
                        if (isEditing) {
                             // Save
                             viewModel.updateProfile(name, phone, selectedImageUri?.toString() ?: resident?.idProofUrl)
                        } else {
                            isEditing = true 
                        }
                    }) {
                        Text(if(isEditing) "Save" else "Edit", color = CorporateBlue, fontWeight = FontWeight.Bold)
                    }
                }
            )
            
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()), 
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                
                // Profile Header with Subscription Badge
                if (resident != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(CorporateBlue), contentAlignment = Alignment.Center) {
                             if (resident!!.profilePictureUrl != null && resident!!.profilePictureUrl!!.isNotEmpty()) {
                                 // Ideally load image
                                 Text(resident!!.name.take(1), style = MaterialTheme.typography.headlineLarge, color = Color.White)
                             } else {
                                Text(resident!!.name.take(1), style = MaterialTheme.typography.headlineLarge, color = Color.White)
                             }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(resident!!.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                            Text("Resident | Room ${resident!!.roomNumber}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            // Subscription Badge
                            Surface(
                                color = Color(0xFFFFF8E1), // Gold/Amber Light
                                contentColor = Color(0xFFF57F17), // Gold Dark
                                shape = CircleShape,
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF57F17).copy(alpha=0.5f))
                            ) {
                                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(androidx.compose.material.icons.Icons.Default.Star, contentDescription = null, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Mess Plan: ${resident!!.plan.name}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
                
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                
                // Editable Info Fields
                if (isEditing) {
                    OutlinedTextField(
                        value = name, onValueChange = { name = it },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = phone, onValueChange = { phone = it },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    // ID Proof Upload
                    CorporateCard {
                        Column {
                             Text("ID Proof Document", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                             Spacer(modifier = Modifier.height(8.dp))
                             if (selectedImageUri != null) {
                                 Text("Selected: ${selectedImageUri!!.lastPathSegment}", color = SuccessGreen, style = MaterialTheme.typography.bodySmall)
                             } else if (resident?.idProofUrl != null) {
                                 Text("Current: ID Proof Uploaded", color = CorporateBlue, style = MaterialTheme.typography.bodySmall)
                             } else {
                                 Text("No document uploaded", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                             }
                             Spacer(modifier = Modifier.height(8.dp))
                             Button(
                                 onClick = { launcher.launch("image/*") },
                                 modifier = Modifier.fillMaxWidth(),
                                 colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
                             ) {
                                 Icon(Icons.Filled.Edit, contentDescription = null)
                                 Spacer(modifier = Modifier.width(8.dp))
                                 Text("Upload ID Proof")
                             }
                        }
                    }

                } else {
                    if (resident != null) {
                        InfoRow("Phone", resident!!.phoneNumber)
                        InfoRow("Room", resident!!.roomNumber)
                        InfoRow("Plan", resident!!.plan.name)
                        InfoRow("Outstanding Dues", "₹${resident!!.outstandingDues}")
                        
                        if (resident!!.idProofUrl != null) {
                            InfoRow("ID Proof", "Document Uploaded (Verified)")
                        } else {
                             InfoRow("ID Proof", "Not Uploaded")
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                
                Button(
                    onClick = { 
                        authViewModel.logout {
                             navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed.copy(alpha=0.1f), contentColor = ErrorRed),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text("Logout", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium, color = SecondaryText)
        Text(value, style = MaterialTheme.typography.bodyLarge, color = PrimaryText, fontWeight = FontWeight.Medium)
    }
}
