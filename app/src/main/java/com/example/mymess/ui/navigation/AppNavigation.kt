package com.example.mymess.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mymess.ui.auth.ModernLoginScreen
import com.example.mymess.ui.auth.RegistrationScreen
import com.example.mymess.ui.owner.BiometricConsoleScreen
import com.example.mymess.ui.resident.MessFeedScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        
        // --- LAUNCH ---
        composable("splash") {
            com.example.mymess.ui.shared.SplashScreen(navController) 
        }

        // --- AUTH ---
        composable("login") {
            ModernLoginScreen(navController)
        }
        composable("register") {
            RegistrationScreen(navController)
        }

        // --- OWNER HUB & SPOKES ---
        composable("owner_hub") {
            com.example.mymess.ui.owner.OwnerHubScreen(navController)
        }
        composable("owner_inventory") {
            com.example.mymess.ui.owner.OwnerInventoryScreen(navController)
        }
        composable("owner_menu") {
             com.example.mymess.ui.owner.OwnerMenuPlannerScreen(navController)
        }
        composable("owner_expenses") {
             com.example.mymess.ui.owner.OwnerExpensesScreen(navController)
        }
        composable("owner_fees") {
             com.example.mymess.ui.owner.OwnerFeesScreen(navController)
        }
        composable("daily_attendance") {
            com.example.mymess.ui.owner.BiometricConsoleScreen(navController)
        }
        composable("mess_profile") {
             com.example.mymess.ui.owner.MessProfileScreen(navController)
        }
        composable("owner_wastage") {
             com.example.mymess.ui.owner.OwnerWastageScreen(navController)
        }

        // --- RESIDENT HUB & SPOKES ---
        composable("resident_hub") {
            com.example.mymess.ui.resident.ResidentHubScreen(navController)
        }
        composable("mess_feed") { // Used for "Pay Now" or General Feed
            com.example.mymess.ui.resident.MessFeedScreen(navController)
        }
        composable("resident_menu") {
            com.example.mymess.ui.resident.ResidentMenuScreen(navController)
        }
        composable("resident_complaints") {
            com.example.mymess.ui.resident.ResidentComplaintsScreen(navController)
        }
        composable("history") {
             com.example.mymess.ui.shared.TransactionHistoryScreen(navController)
        }
        composable("user_profile") {
             com.example.mymess.ui.resident.UserProfileScreen(navController)
        }
        composable("bill_details") {
             com.example.mymess.ui.resident.BillDetailsScreen(navController)
        }
        composable("guest_booking") {
             com.example.mymess.ui.resident.GuestBookingScreen(navController)
        }
        
        // --- REPORTS ---
        composable("owner_reports") {
            com.example.mymess.ui.owner.AdvancedReportsScreen(navController)
        }
        
        // --- NEW FEATURES ---
        composable("guest_orders") {
             com.example.mymess.ui.owner.GuestOrderManagementScreen(navController)
        }
        composable("service_requests") {
             com.example.mymess.ui.resident.ServiceRequestTrackerScreen(navController)
        }
        composable("verification") {
             com.example.mymess.ui.owner.ResidentVerificationScreen(navController)
        }
    }
}
