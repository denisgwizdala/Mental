package com.mentalbuilding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mentalbuilding.ui.screens.TodayScreen
import com.mentalbuilding.ui.theme.MentalBuildingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentalBuildingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "today"
                    ) {
                        composable("today") {
                            TodayScreen(
                                onNavigateToEvening = {
                                    // TODO: Navigate to evening screen
                                }
                            )
                        }

                        // TODO: Add more screens
                        // composable("evening") { EveningScreen() }
                        // composable("history") { HistoryScreen() }
                        // composable("trends") { TrendsScreen() }
                    }
                }
            }
        }
    }
}
