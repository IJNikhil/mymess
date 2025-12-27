package com.example.mymess.ui.resident

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mymess.data.source.HardcodedData
import com.example.mymess.domain.model.ServiceRequest

import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*

@Composable
fun ServiceRequestTrackerScreen(navController: NavController) {
    // Demo Data
    val currentUser = "res-001" // Should be dynamic
    val requests = HardcodedData.serviceRequests.filter { it.residentId == currentUser || it.residentId == "res-demo" } // Fix for demo

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(title = "Service Requests", onBack = { navController.popBackStack() })

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (requests.isEmpty()) {
                    item { 
                        Text("No Service Requests found.", modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.outline)
                    }
                }
                
                items(requests) { req ->
                    RequestStatusCard(req)
                }
            }
        }
    }
}

@Composable
fun RequestStatusCard(req: ServiceRequest) {
    CorporateCard {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "ID: ${req.id}", 
                    style = MaterialTheme.typography.labelSmall, 
                    color = MaterialTheme.colorScheme.outline
                )
                StatusBadge(req.status)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(req.type.replace("_", " "), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = CorporateBlue)
            Spacer(modifier = Modifier.height(4.dp))
            Text(req.description, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            
            if (req.adminResponse != null) {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha=0.5f))
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Response:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(req.adminResponse ?: "", style = MaterialTheme.typography.bodyMedium, color = SuccessGreen, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
