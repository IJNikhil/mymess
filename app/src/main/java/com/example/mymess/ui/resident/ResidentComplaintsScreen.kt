package com.example.mymess.ui.resident

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*
import androidx.compose.material.icons.filled.Menu

@Composable
fun ResidentComplaintsScreen(
    navController: NavController,
    viewModel: ResidentComplaintsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var messageText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(
                title = "Mess Support", 
                onBack = { navController.popBackStack() }
            )

            // Chat Area
            Box(modifier = Modifier.weight(1f)) {
                when (val state = uiState) {
                    is ChatUiState.Loading -> {
                         Column {
                             SkeletonListRow()
                             SkeletonListRow()
                             SkeletonListRow()
                         }
                    }
                    is ChatUiState.Error -> Text(state.message, modifier = Modifier.align(Alignment.Center))
                    is ChatUiState.Success -> {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.messages) { msg ->
                                ChatBubble(msg)
                            }
                        }
                    }
                }
            }

            // Input Area
            CorporateCard(modifier = Modifier.padding(16.dp).imePadding()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ModernInput(
                        value = messageText, 
                        onValueChange = { messageText = it }, 
                        label = "Type message...", 
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { 
                        viewModel.sendMessage(messageText)
                        messageText = ""
                    }) {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = CorporateBlue)
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(msg: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (msg.isUser) Alignment.End else Alignment.Start
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (msg.isUser) CorporateBlue else Color.White
            ),
            shape = RoundedCornerShape(
                topStart = 16.dp, 
                topEnd = 16.dp, 
                bottomStart = if (msg.isUser) 16.dp else 4.dp,
                bottomEnd = if (msg.isUser) 4.dp else 16.dp
            ),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = msg.text,
                modifier = Modifier.padding(12.dp),
                color = if (msg.isUser) Color.White else PrimaryText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = "Just now", // Simplification
            style = MaterialTheme.typography.labelSmall,
            color = SecondaryText,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
