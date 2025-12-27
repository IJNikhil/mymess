package com.example.mymess.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mymess.ui.theme.ErrorRed
import com.example.mymess.ui.theme.SuccessGreen
import com.example.mymess.ui.theme.WarningAmber

@Composable
fun StatusBadge(status: String) {
    val (color, text) = when (status) {
        "PENDING" -> WarningAmber to "Pending"
        "ACCEPTED" -> SuccessGreen to "Accepted"
        "REJECTED" -> ErrorRed to "Rejected"
        "COMPLETED" -> MaterialTheme.colorScheme.outline to "Completed"
        "DUE" -> WarningAmber to "Due"
        "PAID" -> SuccessGreen to "Paid"
        else -> MaterialTheme.colorScheme.primary to status
    }
    
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = color)
        }
    }
}
// Removed HorizontalDivider (Use M3)
// Removed FilterChip (Use M3)
