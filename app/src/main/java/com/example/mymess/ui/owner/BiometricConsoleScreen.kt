package com.example.mymess.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BiometricConsoleScreen(
    navController: NavController,
    viewModel: OwnerBiometricViewModel = hiltViewModel()
) {
    // Renamed UI to "Daily Attendance"
    var filter by remember { mutableStateOf("Present") } // Present, Absent

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(
                title = "Daily Attendance",
                onBack = { navController.popBackStack() }
            )
            
            // Filter Tabs
            Row(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth()) {
                FilterTab("Present", filter == "Present") { filter = "Present" }
                Spacer(modifier = Modifier.width(8.dp))
                FilterTab("Absent", filter == "Not Marked") { filter = "Not Marked" } // Renamed for clarity
                Spacer(modifier = Modifier.width(8.dp))
                FilterTab("Weekly Report", filter == "Report") { filter = "Report" }
            }

            if(filter == "Report") {
                // WEEKLY REPORT VIEW
                Column(modifier = Modifier.padding(24.dp)) {
                    CorporateCard {
                        Column {
                            Text("Weekly Attendance Trend", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Avg. 85 Residents / Day", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            // Visual Chart
                            Row(
                                modifier = Modifier.fillMaxWidth().height(180.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                val days = listOf("M", "T", "W", "T", "F", "S", "S")
                                val values = listOf(0.8f, 0.85f, 0.9f, 0.75f, 0.8f, 0.6f, 0.95f)
                                
                                days.zip(values).forEach { (day, height) ->
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(
                                            modifier = Modifier
                                                .width(24.dp)
                                                .fillMaxHeight(height)
                                                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                                .background(if(height > 0.8f) SuccessGreen else CorporateBlue)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(day, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Stats Grid
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                         StatCard(title = "Best Day", value = "Sunday", color = SuccessGreen, modifier = Modifier.weight(1f))
                         StatCard(title = "Lowest Day", value = "Saturday", color = WarningAmber, modifier = Modifier.weight(1f))
                    }
                }
            } else {
                // LIST VIEW
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                     item {
                          // Table Header (Clean Enterprise Style)
                          Row(modifier = Modifier
                              .fillMaxWidth()
                              .background(MaterialTheme.colorScheme.surface)
                              .padding(horizontal = 24.dp, vertical = 12.dp)
                          ) {
                              Text("NAME", modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                              Text("TIME", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                              Text("STATUS", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                          }
                          HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                      }
                      
                      // Mock Data Logic
                      if (filter == "Present") {
                          items(listOf(
                              Triple("Rahul Sharma", "08:15 AM", "Verified"),
                              Triple("Amit Patel", "08:17 AM", "Verified"),
                              Triple("Sneha Gupta", "08:22 AM", "Verified")
                          )) { (name, time, status) ->
                              AttendanceRow(name, time, status, true)
                          }
                      } else {
                          items(listOf(
                              Triple("Vikram Singh", "-", "Absent"),
                              Triple("Priya K", "-", "Absent")
                          )) { (name, time, status) ->
                              AttendanceRow(name, time, status, false)
                          }
                      }
                }
            }
        }
    }
}

@Composable
fun FilterTab(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if(selected) MaterialTheme.colorScheme.primary else Color.Transparent,
            contentColor = if(selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = if(!selected) androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha=0.5f)) else null,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
        modifier = Modifier.height(40.dp) // Consistent height
    ) {
        Text(text, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AttendanceRow(name: String, time: String, status: String, isPresent: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1.5f)) {
            Text(name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            if(isPresent) {
                 Text("ID: 1024", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            }
        }
        
        Text(time, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        
        // Status Badge
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (isPresent) SuccessGreen.copy(alpha = 0.1f) else ErrorRed.copy(alpha = 0.1f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                status.uppercase(), 
                style = MaterialTheme.typography.labelSmall, 
                color = if(isPresent) SuccessGreen else ErrorRed, 
                fontWeight = FontWeight.Bold
            )
        }
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha=0.3f))
}
