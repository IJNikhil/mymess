package com.example.mymess.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mymess.ui.theme.CorporateBlue
import com.example.mymess.ui.theme.CorporateSlate
import com.example.mymess.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptSheet(
    title: String,
    amount: String,
    date: String,
    transactionId: String,
    status: String = "SUCCESSFUL",
    onDismiss: () -> Unit,
    onDownload: () -> Unit
) {
    CorporateBottomSheet(
        title = "Receipt",
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Receipt Card Visual
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                    .background(Color.White)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("MyMess", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = CorporateBlue)
                        Text(status, style = MaterialTheme.typography.labelMedium, color = SuccessGreen, fontWeight = FontWeight.Bold)
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Amount
                    Text("AMOUNT PAID", style = MaterialTheme.typography.labelSmall, color = CorporateSlate)
                    Text(amount, style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Details
                    ReceiptRow("Transaction ID", transactionId)
                    ReceiptRow("Date", date)
                    ReceiptRow("Payment For", title)
                    ReceiptRow("Payment Mode", "Online (UPI)")
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(), 
                        thickness = 1.dp, 
                        color = CorporateSlate.copy(alpha=0.5f),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Thank you for your payment!", 
                        style = MaterialTheme.typography.bodySmall, 
                        color = CorporateSlate,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onDownload,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CorporateBlue)
            ) {
                 Text("Download Receipt")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = CorporateSlate)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun HorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: androidx.compose.ui.unit.Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outlineVariant,
    pathEffect: PathEffect? = null
) {
    androidx.compose.foundation.Canvas(modifier = modifier.height(thickness)) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = thickness.toPx(),
            pathEffect = pathEffect
        )
    }
}
