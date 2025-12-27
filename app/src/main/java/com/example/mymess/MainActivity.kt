package com.example.mymess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge // "Modern Android"
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.mymess.ui.navigation.AppNavigation
import com.example.mymess.ui.theme.MyMessTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 1. "Software Minded": Enable Edge-to-Edge for that immersive, premium feel.
        // This draws behind status and navigation bars.
        enableEdgeToEdge()
        
        setContent {
            // 2. Wrap in Theme
            MyMessTheme {
                // 3. Surface with background color from theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(navController = navController)
                }
            }
        }
    }
}
