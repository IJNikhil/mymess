package com.example.mymess.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mymess.ui.theme.CorporateBlue
import com.example.mymess.ui.theme.CorporateNavy

@Composable
fun CorporateDialog(
    onDismissRequest: () -> Unit,
    title: String,
    content: @Composable ColumnScope.() -> Unit,
    confirmButtonText: String = "Confirm",
    onConfirm: (() -> Unit)? = null,
    dismissButtonText: String = "Cancel",
    onDismiss: (() -> Unit)? = null,
    isCritical: Boolean = false
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), // Less padding around for wider feel
            shape = RoundedCornerShape(28.dp), // Apple-style large roundness
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh), // Higher contrast surface
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp) // Generous internal padding
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Content
                content()

                Spacer(modifier = Modifier.height(32.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End, // Standard Right align
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (onDismiss != null || dismissButtonText.isNotEmpty()) {
                        TextButton(
                            onClick = { onDismiss?.invoke() ?: onDismissRequest() },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                dismissButtonText,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    if (onConfirm != null) {
                        Button(
                            onClick = onConfirm,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCritical) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(50), // Pill Shape
                            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                        ) {
                            Text(confirmButtonText, style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }
    }
}
