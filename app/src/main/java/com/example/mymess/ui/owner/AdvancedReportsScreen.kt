package com.example.mymess.ui.owner

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AdvancedReportsScreen(
    navController: NavController,
    viewModel: OwnerReportsViewModel = hiltViewModel()
) {
    // Phase 21: Real Data & Date Picker
    var selectedFilter by remember { mutableStateOf("Monthly") }
    val filters = listOf("Daily", "Weekly", "Monthly", "Annually", "Custom")
    
    val reportData by viewModel.reportState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    // Date Picker State
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDateRangePickerState()

    // Load data on filter change
    LaunchedEffect(selectedFilter) {
        if (selectedFilter != "Custom") {
            viewModel.loadReport(selectedFilter)
        } else {
            showDatePicker = true
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { 
                showDatePicker = false 
                selectedFilter = "Monthly" // Revert if cancelled
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        val start = datePickerState.selectedStartDateMillis ?: 0L
                        val end = datePickerState.selectedEndDateMillis ?: System.currentTimeMillis()
                        viewModel.loadReport("Custom", start, end)
                    }
                ) { Text("Apply") }
            },
            dismissButton = {
                TextButton(onClick = { 
                    showDatePicker = false
                    selectedFilter = "Monthly"
                }) { Text("Cancel") }
            }
        ) {
            DateRangePicker(state = datePickerState)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            BackHeader(title = "Financial Reports", onBack = { navController.popBackStack() })
            
            // 1. Time Filter Segment
            ScrollableTabRow(
                selectedTabIndex = filters.indexOf(selectedFilter),
                containerColor = Color.Transparent,
                contentColor = CorporateBlue,
                edgePadding = 24.dp,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[filters.indexOf(selectedFilter)]),
                        color = CorporateBlue,
                        height = 3.dp
                    )
                }
            ) {
                filters.forEach { filter ->
                    Tab(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        text = { 
                            Text(
                                filter, 
                                style = if(selectedFilter==filter) MaterialTheme.typography.titleSmall else MaterialTheme.typography.bodyMedium,
                                fontWeight = if(selectedFilter==filter) FontWeight.Bold else FontWeight.Normal
                            ) 
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = CorporateBlue)
                }
            } else {
                Column(modifier = Modifier.padding(24.dp)) {
                    
                    // 2. Main Chart Area with Tabs
                    var chartTab by remember { mutableStateOf(0) }
                    val chartTabs = listOf("Revenue Trend", "Expense Comparison")
                    
                    TabRow(
                        selectedTabIndex = chartTab,
                        containerColor = Color.Transparent,
                        divider = {},
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier.tabIndicatorOffset(tabPositions[chartTab]),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    ) {
                        chartTabs.forEachIndexed { index, title ->
                            Tab(
                                selected = chartTab == index,
                                onClick = { chartTab = index },
                                text = { Text(title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurface) }
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    CorporateCard {
                        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                             if (chartTab == 0) {
                                 // LINE CHART (Trend)
                                 Text("Last 6 Months Income", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                 Spacer(modifier = Modifier.height(24.dp))
                                 SimpleLineChart(
                                     dataPoints = listOf(10f, 15f, 12f, 18f, 22f, 25f), // Mock Trend
                                     lineColor = SuccessGreen,
                                     modifier = Modifier.fillMaxWidth().height(150.dp)
                                 )
                             } else {
                                 // BAR CHART (Comparison)
                                 Text("Category Breakdown (k)", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                 Spacer(modifier = Modifier.height(24.dp))
                                 EnterpriseBarChart(
                                     data = listOf(
                                         "Groceries" to 12.5f,
                                         "Veg" to 8.2f,
                                         "Dairy" to 5.5f,
                                         "Staff" to 15.0f,
                                         "Utils" to 4.0f
                                     ),
                                     maxVal = 20f,
                                     barColor = ErrorRed,
                                     modifier = Modifier.fillMaxWidth().height(150.dp)
                                 )
                             }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // 3. Breakdown Stats
                    Text("Key Metrics", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Using BusinessStatCard (8dp) for consistent visuals
                        BusinessStatCard(
                            title = "Revenue", 
                            value = "₹${reportData.revenue.toInt()}", 
                            trend = "+12%", 
                            trendColor = SuccessGreen, 
                            icon = Icons.Default.CurrencyRupee,
                            modifier = Modifier.weight(1f)
                        )
                         BusinessStatCard(
                            title = "Net Profit", 
                            value = "₹${reportData.profit.toInt()}", 
                            trend = "+8%", 
                            trendColor = CorporateBlue, 
                            icon = Icons.Default.TrendingUp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
