package com.example.mymess.ui.resident

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillDetailsScreen(
    navController: NavController,
    viewModel: ResidentPaymentViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val resident by viewModel.resident.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    
    var showPaymentSheet by remember { mutableStateOf(false) }
    var paymentStep by remember { mutableStateOf(1) }

    // Navigation on Success
    LaunchedEffect(uiState) {
        if (uiState is PaymentUiState.Success) {
            // Wait for user to dismiss success sheet which is handled by UI
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        if (resident == null) {
             CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val total = resident!!.outstandingDues
            
            Column(modifier = Modifier.fillMaxSize()) {
                BackHeader("Bill Details", onBack = { navController.popBackStack() })

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        CorporateCard {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                                Text("Outstanding Balance", style = MaterialTheme.typography.titleSmall, color = SecondaryText)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("₹$total", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold, color = CorporateBlue)
                                Spacer(modifier = Modifier.height(8.dp))
                                StatusBadge(if (total > 0) "DUE" else "PAID")
                            }
                        }
                    }
                    
                    item {
                        Text("Breakdown", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }

                    // Mock Items
                    item {
                        CorporateCard {
                            Column {
                                BillItemRow("Mess Fees (Oct)", "₹3000")
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                                BillItemRow("Guest Meal (2x)", "₹200")
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                                BillItemRow("Late Fee", "₹0")
                            }
                        }
                    }
                }
            }
            
            // Bottom Action
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Button(
                    onClick = { showPaymentSheet = true },
                    enabled = total > 0 && uiState !is PaymentUiState.Processing,
                    modifier = Modifier.fillMaxWidth().padding(24.dp).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CorporateBlue)
                ) {
                    if (uiState is PaymentUiState.Processing) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Proceed to Pay ₹$total", fontSize = MaterialTheme.typography.titleMedium.fontSize)
                    }
                }
            }
        }
        
        // Payment Confirmation Sheet
        if (showPaymentSheet && resident != null) {
            CorporateBottomSheet(
                title = when(paymentStep) {
                    1 -> "Select Payment Method"
                    2 -> "Confirm Payment"
                    3 -> "Payment Successful"
                    else -> "Payment"
                },
                onDismissRequest = { 
                    showPaymentSheet = false 
                    paymentStep = 1 // Reset
                }
            ) {
                 when (paymentStep) {
                     1 -> {
                         // Step 1: Select Method
                         Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                             PaymentMethodOption("UPI Check / QR", selected = true, onClick = {})
                             PaymentMethodOption("Credit / Debit Card", selected = false, onClick = {})
                             PaymentMethodOption("Net Banking", selected = false, onClick = {})
                             Spacer(modifier = Modifier.height(16.dp))
                             Button(
                                 onClick = { paymentStep = 2 },
                                 modifier = Modifier.fillMaxWidth().height(48.dp),
                                 colors = ButtonDefaults.buttonColors(containerColor = CorporateBlue)
                             ) {
                                 Text("Next")
                             }
                         }
                     }
                     2 -> {
                         // Step 2: Confirm
                         Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                             Text("Amount to Pay: ₹${resident!!.outstandingDues}", style = MaterialTheme.typography.titleMedium)
                             Text("Method: UPI", style = MaterialTheme.typography.bodyMedium, color = SecondaryText)
                             Spacer(modifier = Modifier.height(8.dp))
                             Button(
                                 onClick = { 
                                     viewModel.submitPayment(resident!!.outstandingDues, "UPI") 
                                     // For simulated flow, we manually advance step.
                                     // Real app relies on LaunchedEffect observing uiState, but this keeps visual flow.
                                     paymentStep = 3
                                 },
                                 modifier = Modifier.fillMaxWidth().height(48.dp),
                                 colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                             ) {
                                 Text("Pay Now")
                             }
                         }
                     }
                     3 -> {
                         // Step 3: Success
                         Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                             Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(64.dp))
                             Spacer(modifier = Modifier.height(16.dp))
                             Text("Payment Successful!", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                             Spacer(modifier = Modifier.height(8.dp))
                             Text("Receipt generated in History.", style = MaterialTheme.typography.bodyMedium, color = SecondaryText)
                             Spacer(modifier = Modifier.height(24.dp))
                             Button(
                                 onClick = { 
                                     showPaymentSheet = false 
                                     paymentStep = 1
                                 },
                                 modifier = Modifier.fillMaxWidth(),
                                 colors = ButtonDefaults.buttonColors(containerColor = CorporateBlue)
                             ) {
                                 Text("Close")
                             }
                         }
                     }
                 }
            }
        }
    }
}

@Composable
fun PaymentMethodOption(label: String, selected: Boolean, onClick: () -> Unit) {
    CorporateCard(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        border = if(selected) BorderStroke(2.dp, CorporateBlue) else null
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            RadioButton(selected = selected, onClick = onClick)
            Text(label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun BillItemRow(label: String, amount: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), 
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(amount, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}
