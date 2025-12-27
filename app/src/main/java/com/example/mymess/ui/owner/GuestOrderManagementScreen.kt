package com.example.mymess.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mymess.data.source.HardcodedData
import com.example.mymess.domain.model.ServiceRequest
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*

@Composable
fun GuestOrderManagementScreen(
    navController: NavController,
    viewModel: OwnerServiceRequestViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val requests by viewModel.requests.collectAsState()
    var selectedRequest by remember { mutableStateOf<ServiceRequest?>(null) } // For Action Sheet

    // Action Sheet Logic
    if (selectedRequest != null) {
        RequestActionSheet(
            request = selectedRequest!!,
            onDismiss = { selectedRequest = null },
            onUpdateStatus = { req, status, response ->
                viewModel.updateRequestStatus(req, status, response)
                selectedRequest = null
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(title = "Guest Orders & Requests", onBack = { navController.popBackStack() })

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // PENDING SECTION
                item { SectionTitle("Pending Action") }
                val pending = requests.filter { it.status == "PENDING" }
                if (pending.isEmpty()) {
                    item { EmptyState("No pending requests") }
                } else {
                    items(pending) { req ->
                        RequestCard(req) { selectedRequest = req }
                    }
                }

                // IN PROGRESS / COMPLETED
                item { SectionTitle("History") }
                val history = requests.filter { it.status != "PENDING" }
                items(history) { req ->
                    RequestCard(req) { selectedRequest = req }
                }
            }
        }
    }
}

@Composable
fun RequestCard(req: ServiceRequest, onClick: () -> Unit) {
    CorporateCard(modifier = Modifier.clickable(onClick = onClick)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(req.type.replace("_", " "), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = CorporateBlue)
                StatusBadge(req.status)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(req.description, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(req.residentId, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) // Ideally User Name
                if (req.adminResponse != null) {
                    Text(req.adminResponse ?: "", style = MaterialTheme.typography.labelSmall, color = SuccessGreen, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// StatusBadge moved to SharedComponents.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestActionSheet(
    request: ServiceRequest,
    onDismiss: () -> Unit,
    onUpdateStatus: (ServiceRequest, String, String?) -> Unit
) {
    var priceInput by remember { mutableStateOf("") }
    
    CorporateBottomSheet(onDismissRequest = onDismiss, title = "Manage Request") {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Request: ${request.description}", style = MaterialTheme.typography.bodyMedium)
            
            if (request.status == "PENDING") {
                ModernInput(
                    value = priceInput, 
                    onValueChange = { priceInput = it }, 
                    label = "Quote Price / Comment",
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Text)
                )
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { onUpdateStatus(request, "REJECTED", "Declined") },
                        colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) { Text("Reject") }
                    
                    Button(
                        onClick = { onUpdateStatus(request, "ACCEPTED", if(priceInput.isEmpty()) "Accepted" else "Price: $priceInput") },
                        colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) { Text("Accept & Quote") }
                }
            } else if (request.status == "ACCEPTED") {
                 Button(
                    onClick = { onUpdateStatus(request, "COMPLETED", request.adminResponse) },
                    colors = ButtonDefaults.buttonColors(containerColor = CorporateBlue),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) { Text("Mark Completed") }
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text, 
        style = MaterialTheme.typography.titleSmall, 
        fontWeight = FontWeight.Bold, 
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun EmptyState(text: String) {
    Text(
        text, 
        style = MaterialTheme.typography.bodyMedium, 
        color = MaterialTheme.colorScheme.outline,
        modifier = Modifier.fillMaxWidth().padding(32.dp).wrapContentWidth()
    )
}
