package com.example.mymess.ui.resident

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestBookingScreen(
    navController: NavController,
    viewModel: GuestBookingViewModel = hiltViewModel()
) {
    var guestCount by remember { mutableIntStateOf(1) }
    var selectedSlot by remember { mutableStateOf("Lunch") }
    var specialRequest by remember { mutableStateOf("") }
    val slots = listOf("Breakfast", "Lunch", "Dinner")

    val bookingState by viewModel.bookingState.collectAsState()
    val requests by viewModel.requests.collectAsState()

    // Handle State Changes
    LaunchedEffect(bookingState) {
        if (bookingState is GuestBookingState.Success) {
            // Optional: Show success snackbar or dialog?
            // For now, assume VM resets state or we just show list update.
            // Pop back might be confusing if user wants to see the request added.
            // Let's NOT pop, just clear form?
            // Existing code popped. Keeping relevant to user flow.
            // Actually, if we pop, user leaves screen. Let's stay and show history.
            // But if user requested specifically, maybe pop is standard.
            // I'll keep existing behavior but maybe clear inputs.
            // navController.popBackStack() 
            // Commented out to let user see "Pending" request in history immediately.
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(title = "Guest Booking", onBack = { navController.popBackStack() })

            // Content
            LazyColumn(
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Form Section
                item {
                    CorporateCard {
                        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                            
                            // 1. Guest Count
                            Column {
                                Text("Num. of Guests", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Button(onClick = { if(guestCount > 1) guestCount-- }) { Text("-") }
                                    Text(
                                        "$guestCount", 
                                        style = MaterialTheme.typography.headlineMedium, 
                                        modifier = Modifier.padding(horizontal = 24.dp),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Button(onClick = { if(guestCount < 10) guestCount++ }) { Text("+") }
                                }
                            }

                            // 2. Meal Slot
                            Column {
                                Text("Meal Slot", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    slots.forEach { slot ->
                                        FilterChip(
                                            selected = selectedSlot == slot,
                                            onClick = { selectedSlot = slot },
                                            label = { Text(slot) },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                            )
                                        )
                                    }
                                }
                            }
                            
                            // 3. Special Request / Note
                            ModernInput(
                                value = specialRequest,
                                onValueChange = { specialRequest = it },
                                label = "Note / Special Request (Optional)",
                                modifier = Modifier.fillMaxWidth().height(100.dp),
                                singleLine = false
                            )

                            // 4. Submit
                            CorporateButton(
                                text = if(bookingState is GuestBookingState.Loading) "Submitting..." else "Raise Service Request",
                                onClick = {
                                    viewModel.bookGuestMeal(guestCount, selectedSlot, specialRequest)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            if (bookingState is GuestBookingState.Error) {
                                Text(
                                    (bookingState as GuestBookingState.Error).message, 
                                    color = MaterialTheme.colorScheme.error, 
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
                
                // History Section
                item {
                    Text("Your Requests", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }

                if (requests.isEmpty()) {
                    item {
                        Text("No requests found.", color = MaterialTheme.colorScheme.outline)
                    }
                } else {
                    items(requests) { req ->
                        RequestHistoryCard(req, onConfirm = { viewModel.confirmQuote(req.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun RequestHistoryCard(req: com.example.mymess.domain.model.ServiceRequest, onConfirm: () -> Unit) {
    CorporateCard {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Header
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(req.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                StatusBadge(req.status)
            }
            
            // Details
            Text(req.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            
            // Pricing / Status Logic
            if (req.status == "ACCEPTED" && req.quotedPrice != null) {
                HorizontalDivider()
                Row(
                    modifier = Modifier.fillMaxWidth(), 
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Quoted Price", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹${req.quotedPrice}", style = MaterialTheme.typography.titleMedium, color = SuccessGreen, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(containerColor = CorporateBlue),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Confirm & Pay", style = MaterialTheme.typography.labelSmall)
                    }
                }
                if (req.adminResponse != null) {
                     Text("Admin: ${req.adminResponse}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else if (req.status == "IN_PROGRESS") {
                 HorizontalDivider()
                 Text("Added to Bill: ₹${req.quotedPrice}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
