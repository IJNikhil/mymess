package com.example.mymess.ui.resident

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape 
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Added
import androidx.navigation.NavController
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*

@Composable
fun ResidentHubScreen(navController: NavController) {
    // Zero-Bar Architecture: Content First.

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            
            // 1. Hub Header
            HubHeader(
                title = "Good Morning,",
                subtitle = "Vijay Kumar",
                onProfileClick = { navController.navigate("user_profile") }
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                
               // 2. Bill Summary (Detailed)
                item {
                    CorporateCard {
                        Column(modifier = Modifier.padding(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.size(40.dp).background(ErrorRed.copy(alpha=0.1f), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.ReceiptLong, null, tint = ErrorRed)
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text("Total Outstanding", style = MaterialTheme.typography.labelMedium, color = SecondaryText)
                                        Text("₹ 3,350.00", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = PrimaryText)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Mini Details
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                BillMiniStat("Period", "Dec 2024")
                                BillMiniStat("Due Date", "05 Jan")
                                BillMiniStat("Status", "Pending", isCritical = true)
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            CorporateButton(
                                text = "Pay Now",
                                onClick = { navController.navigate("bill_details") },
                                modifier = Modifier.fillMaxWidth().height(48.dp)
                            )
                        }
                    }
                }

                // 3. Menu Highlights (Detailed)
                item {
                    Text("Upcoming Meals", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PrimaryText)
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Today's Menu Breakdown
                    CorporateCard {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            MenuRow("Breakfast", "Idli, Vada, Sambhar", "07:30 - 09:30")
                            HorizontalDivider(color = CorporateLightGray.copy(alpha=0.5f))
                            MenuRow("Lunch", "Rice, Dal, Paneer Curry", "12:30 - 02:30", isHighlight = true)
                            HorizontalDivider(color = CorporateLightGray.copy(alpha=0.5f))
                            MenuRow("Dinner", "Chapati, Mix Veg, Curd", "07:30 - 09:30")
                        }
                    }
                }

                // 4. Quick Access Grid
                 item {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        DashboardTile(
                            title = "Full Menu",
                            icon = Icons.Default.RestaurantMenu,
                            color = CorporateBlue,
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("resident_menu") }
                        )
                        DashboardTile(
                            title = "Raise Request",
                            icon = Icons.Default.PersonAdd, // Or specific Icon
                            color = SuccessGreen,
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("guest_booking") } // Renamed logic to Raise Request
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        DashboardTile(
                            title = "My Requests",
                            icon = Icons.Default.DateRange, // Safer Icon
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("service_requests") } // New Route
                        )
                        DashboardTile(
                            title = "Support",
                            icon = Icons.Default.SupportAgent,
                            color = WarningAmber,
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("resident_complaints") }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    DashboardTile(
                        title = "Payment History",
                        icon = Icons.Default.History,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        onClick = { navController.navigate("history") }
                    )
                }
            }
        }
    }
}

@Composable
fun BillMiniStat(label: String, value: String, isCritical: Boolean = false) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall, color = SecondaryText)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = if(isCritical) ErrorRed else PrimaryText)
    }
}

@Composable
fun MenuRow(meal: String, items: String, time: String, isHighlight: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(meal, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = if(isHighlight) CorporateBlue else PrimaryText)
                if(isHighlight) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.background(CorporateBlue.copy(alpha=0.1f), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text("UPCOMING", style = MaterialTheme.typography.labelSmall, color = CorporateBlue, fontSize = 10.sp)
                    }
                }
            }
            Text(items, style = MaterialTheme.typography.bodyMedium, color = SecondaryText, maxLines = 1)
        }
        Text(time, style = MaterialTheme.typography.labelSmall, color = SecondaryText)
    }
}

@Composable
fun DashboardTile(
    title: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(icon, contentDescription = null, tint = color)
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = PrimaryText)
        }
    }
}
