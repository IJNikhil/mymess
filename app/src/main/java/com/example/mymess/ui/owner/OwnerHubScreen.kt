package com.example.mymess.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
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
import androidx.navigation.NavController
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Warning

@Composable
fun OwnerHubScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // DYNAMIC
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            
            // 1. Hub Header
            HubHeader(
                title = "Mess Control",
                subtitle = "Vikram Singh",
                onProfileClick = { navController.navigate("mess_profile") }
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                
                // 2. KEY PERFORMANCE INDICATORS (KPIs)
                item {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                         Text("Business Overview", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                         // Verification Entry Hint
                         TextButton(onClick = { navController.navigate("verification") }) {
                             Text("Verifications", color = CorporateBlue, fontWeight = FontWeight.Bold)
                         }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        BusinessStatCard(
                            title = "Revenue",
                            value = "₹ 12.5L",
                            trend = "+12%",
                            trendColor = SuccessGreen,
                            icon = Icons.Default.CurrencyRupee,
                            modifier = Modifier.width(160.dp),
                            onClick = { navController.navigate("owner_reports") }
                        )
                        BusinessStatCard(
                            title = "Net Profit",
                            value = "₹ 4.2L",
                            trend = "+8.5%",
                            trendColor = CorporateBlue,
                            icon = Icons.Default.TrendingUp,
                            modifier = Modifier.width(160.dp),
                             onClick = { navController.navigate("owner_reports") }
                        )
                        BusinessStatCard(
                            title = "Pending Dues",
                            value = "₹ 45k",
                            trend = "-2.1%", // Negative trend in dues is good? Actually let's say +5% matches Red
                            trendColor = ErrorRed,
                            icon = Icons.Default.Warning,
                            modifier = Modifier.width(160.dp),
                            onClick = { navController.navigate("owner_fees") }
                        )
                    }
                }

                // 3. PENDING DUES CARD
                item {
                    CorporateCard {
                        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text("Outstanding Dues", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("₹ 45,000", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = ErrorRed)
                            }
                            CorporateButton(
                                text = "Tracker", 
                                onClick = { navController.navigate("owner_fees") },
                                modifier = Modifier.height(40.dp).width(100.dp),
                                containerColor = ErrorRed
                            )
                        }
                    }
                }

                // 3. Attendance "Widget"
                item {
                    SectionHeader(title = "Live Attendance", action = "View All", onClick = { navController.navigate("daily_attendance") })
                    Spacer(modifier = Modifier.height(8.dp))
                    CorporateCard {
                        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceAround) {
                            StatItem("Breakfast", "45/120")
                            StatItem("Lunch", "--/120")
                        }
                    }
                }
                
                // 4. TOP DEFAULTERS (NEW)
                item {
                   Text("Top Defaulters", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                   Spacer(modifier = Modifier.height(8.dp))
                   CorporateCard {
                       Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                           DefaulterRow("Rahul Sharma", "₹ 3,000")
                           HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                           DefaulterRow("Vikram Singh", "₹ 4,500")
                           HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                           DefaulterRow("Arjun K", "₹ 1,200")
                       }
                   }
                }

                // 5. Kitchen Management
                item {
                     Text("Kitchen Management", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                     Spacer(modifier = Modifier.height(12.dp))
                     Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        DashboardTile(
                            title = "Menu Plan",
                            icon = Icons.Default.RestaurantMenu,
                            color = CorporateBlue,
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("owner_menu") }
                        )
                        DashboardTile(
                            title = "Inventory",
                            icon = Icons.Default.Inventory,
                            color = MaterialTheme.colorScheme.tertiary, // DYNAMIC
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("owner_inventory") }
                        )
                    }
                     Spacer(modifier = Modifier.height(12.dp))
                     Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                         DashboardTile(
                             title = "Guest Orders",
                             icon = Icons.Default.List, // Safer Icon
                             color = MaterialTheme.colorScheme.primary,
                             modifier = Modifier.weight(1f),
                             onClick = { navController.navigate("guest_orders") }
                         )
                         DashboardTile(
                             title = "Wastage Tracker",
                             icon = Icons.Default.DeleteSweep,
                             color = ErrorRed,
                             modifier = Modifier.weight(1f),
                             onClick = { navController.navigate("owner_wastage") }
                         )
                     }
                 }
            }
        }
    }
}

@Composable
fun DefaulterRow(name: String, amount: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(name, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text(amount, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = ErrorRed)
    }
}

@Composable
fun SectionHeader(title: String, action: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        Text(action, style = MaterialTheme.typography.bodyMedium, color = CorporateBlue, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable(onClick = onClick))
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
             Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color)
            }
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
