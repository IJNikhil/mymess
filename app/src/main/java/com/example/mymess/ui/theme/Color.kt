package com.example.mymess.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

// MyMess 3.0 Corporate Palette
val CorporateNavy = Color(0xFF0F172A) // Primary Background / Text
val CorporateSlate = Color(0xFF64748B) // Secondary Text
val CorporateWhite = Color(0xFFFFFFFF) // Cards
val CorporateBlue = Color(0xFF2563EB) // Action / Highlight
val CorporateLightGray = Color(0xFFF1F5F9) // App Background

val SuccessGreen = Color(0xFF10B981)
val ErrorRed = Color(0xFFEF4444)
val WarningAmber = Color(0xFFF59E0B)

// Semantic - Dynamic (Must be called within Composable context)
val PrimaryText @Composable get() = MaterialTheme.colorScheme.onBackground
val SecondaryText @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant
val AppBackground @Composable get() = MaterialTheme.colorScheme.background
val CardBackground @Composable get() = MaterialTheme.colorScheme.surface
