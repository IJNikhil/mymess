package com.example.mymess.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymess.ui.theme.ErrorRed
import com.example.mymess.domain.model.DailyMenu
import com.example.mymess.ui.shared.*
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.FreeBreakfast
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset

@Composable
fun OwnerMenuPlannerScreen(
    navController: NavController,
    viewModel: OwnerMenuViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    
    // Dialog State
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedSlot by remember { mutableStateOf<Pair<String, String>?>(null) } // SlotName, Content

    if (showEditDialog && selectedSlot != null) {
        EditMenuSheet(
            slotName = selectedSlot!!.first,
            currentMenu = selectedSlot!!.second,
            onDismiss = { showEditDialog = false },
            onSave = { newContent ->
                // Determine day based on tab
                 if (uiState is OwnerMenuUiState.Success) {
                     val day = (uiState as OwnerMenuUiState.Success).menu[selectedTabIndex].dayOfWeek
                     viewModel.updateMenu(day, selectedSlot!!.first, newContent)
                 }
                showEditDialog = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(
                title = "Menu Planner", 
                onBack = { navController.popBackStack() }
            )

            when (val state = uiState) {
                is OwnerMenuUiState.Loading -> {
                     Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(24.dp)) {
                         SkeletonFeedCard()
                         SkeletonFeedCard()
                     }
                }
                is OwnerMenuUiState.Error -> Text(state.message, modifier = Modifier.align(Alignment.CenterHorizontally), color = ErrorRed)
                is OwnerMenuUiState.Success -> {
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
                    
                    EditableMenuContent(
                        menu = state.menu[selectedTabIndex],
                        modifier = Modifier.padding(24.dp),
                        onEditClick = { slot, content ->
                            selectedSlot = slot to content
                            showEditDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EditableMenuContent(
    menu: DailyMenu, 
    modifier: Modifier = Modifier,
    onEditClick: (String, String) -> Unit
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item { EditableMenuItemCard("Breakfast", menu.breakfast, Icons.Default.FreeBreakfast, Color(0xFFFFB74D), onEditClick) }
        item { EditableMenuItemCard("Lunch", menu.lunch, Icons.Default.Restaurant, Color(0xFF64B5F6), onEditClick) }
        item { EditableMenuItemCard("Dinner", menu.dinner, Icons.Default.DinnerDining, Color(0xFF9575CD), onEditClick) }
        if (menu.special != null) {
            item { EditableMenuItemCard("Special", menu.special!!, Icons.Default.Star, Color(0xFFFFD54F), onEditClick) }
        }
    }
}

@Composable
fun EditableMenuItemCard(slot: String, items: String, icon: androidx.compose.ui.graphics.vector.ImageVector, accentColor: Color, onEditClick: (String, String) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha=0.3f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, 
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
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
            
            IconButton(onClick = { onEditClick(slot, items) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
