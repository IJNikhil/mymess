package com.example.mymess.ui.resident

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymess.domain.model.DailyMenu
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.FreeBreakfast
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.ui.graphics.Color

@Composable
fun ResidentMenuScreen(
    navController: NavController,
    viewModel: ResidentMenuViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(
                title = "Weekly Menu", 
                onBack = { navController.popBackStack() }
            )

            when (val state = uiState) {
                is MenuUiState.Loading -> {
                     Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                         SkeletonFeedCard()
                         SkeletonFeedCard()
                     }
                }
                is MenuUiState.Error -> Text(state.message, modifier = Modifier.align(Alignment.CenterHorizontally), color = ErrorRed)
                is MenuUiState.Success -> {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary,
                        edgePadding = 16.dp,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = MaterialTheme.colorScheme.primary,
                                height = 3.dp
                            )
                        }
                    ) {
                        state.menu.forEachIndexed { index, dailyMenu ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = { 
                                    Text(
                                        dailyMenu.dayOfWeek.substring(0, 3).uppercase(), 
                                        fontWeight = if(selectedTabIndex==index) FontWeight.Bold else FontWeight.Medium
                                    ) 
                                },
                                selectedContentColor = MaterialTheme.colorScheme.primary,
                                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    MenuContent(
                        menu = state.menu[selectedTabIndex],
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MenuContent(menu: DailyMenu, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item { MenuItemCard("Breakfast", menu.breakfast, androidx.compose.material.icons.Icons.Default.FreeBreakfast, Color(0xFFFFB74D)) }
        item { MenuItemCard("Lunch", menu.lunch, androidx.compose.material.icons.Icons.Default.Restaurant, Color(0xFF64B5F6)) }
        item { MenuItemCard("Dinner", menu.dinner, androidx.compose.material.icons.Icons.Default.DinnerDining, Color(0xFF9575CD)) }
        if (menu.special != null) {
            item { MenuItemCard("Special", menu.special!!, androidx.compose.material.icons.Icons.Default.Star, Color(0xFFFFD54F)) }
        }
    }
}

@Composable
fun MenuItemCard(slot: String, items: String, icon: androidx.compose.ui.graphics.vector.ImageVector, accentColor: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha=0.3f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, 
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
             // Colorful Icon Box
             Box(
                 modifier = Modifier
                     .size(48.dp)
                     .background(accentColor.copy(alpha = 0.1f), CircleShape),
                 contentAlignment = Alignment.Center
             ) {
                 Icon(icon, contentDescription = null, tint = accentColor)
             }
             
             Spacer(modifier = Modifier.width(16.dp))
             
             Column {
                 Text(slot, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                 Spacer(modifier = Modifier.height(4.dp))
                 Text(items, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
             }
        }
    }
}
