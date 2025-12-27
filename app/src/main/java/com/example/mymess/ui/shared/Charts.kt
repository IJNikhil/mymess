package com.example.mymess.ui.shared

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import com.example.mymess.ui.theme.CorporateBlue
import com.example.mymess.ui.theme.ErrorRed
import com.example.mymess.ui.theme.SuccessGreen

@Composable
fun EnterpriseBarChart(
    data: List<Pair<String, Float>>, // Label, Value
    maxVal: Float,
    barColor: Color = CorporateBlue,
    modifier: Modifier = Modifier
) {
    val barHeightAnim = remember { Animatable(0f) }
    LaunchedEffect(data) {
        barHeightAnim.animateTo(1f, animationSpec = tween(1000))
    }

    // Use BoxWithConstraints to decide if scrolling is needed
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val minWidthPerBar = 60.dp
        val totalWidth = minWidthPerBar * data.size
        
        // Wrap in Row with horizontalScroll if content is too wide
        val scrollState = androidx.compose.foundation.rememberScrollState()
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = 16.dp),
            horizontalArrangement = if(totalWidth > maxWidth) Arrangement.spacedBy(16.dp) else Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            data.forEach { (label, value) ->
                val heightFraction = (value / maxVal).coerceIn(0.1f, 1f) * barHeightAnim.value
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Value Label
                    Text(
                        text = "${value.toInt()}k",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp, // [Fixed] Visible font size
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Bar
                    Box(
                        modifier = Modifier
                            .width(32.dp) // [Fixed] Wider bars
                            .fillMaxHeight(heightFraction)
                            .background(barColor, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)) // [Fixed] Softer corners
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Axis Label
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp // [Fixed] Visible font size
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleLineChart(
    dataPoints: List<Float>,
    lineColor: Color = SuccessGreen,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        if (dataPoints.isEmpty()) return@Canvas
        
        val path = Path()
        val spacing = size.width / (dataPoints.size - 1)
        val maxVal = dataPoints.maxOrNull() ?: 1f
        val minVal = dataPoints.minOrNull() ?: 0f // Normalize? Usually 0 baseline is better for business
        val range = if ((maxVal - 0f) == 0f) 1f else (maxVal - 0f)
        
        dataPoints.forEachIndexed { index, value ->
            val x = index * spacing
            val y = size.height - ((value / range) * size.height) // Invert Y
            
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
            
            // Draw Point
            drawCircle(
                color = lineColor,
                radius = 8f,
                center = Offset(x, y)
            )
        }
        
        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 6f, cap = StrokeCap.Round)
        )
    }
}
