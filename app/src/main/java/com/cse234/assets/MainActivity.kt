package com.cse234.assets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cse234.assets.screens.ActivitiesScreen
import com.cse234.assets.screens.ActivityViewModel
import com.cse234.assets.screens.HomeScreen
import com.cse234.assets.screens.LoginProcedure
import com.cse234.assets.screens.LoginScreen
import com.cse234.assets.screens.RegisterProcedure
import com.cse234.assets.screens.TimerScreenContent
import com.cse234.assets.screens.TimerViewModel
import com.cse234.assets.screens.UserProfileScreen
import com.cse234.assets.ui.theme.AssetsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssetsTheme {

                val navController = rememberNavController()
                val timerViewModel: TimerViewModel by viewModels()
                val activityViewModel : ActivityViewModel by viewModels()
                NavHost(
                    navController = navController,
                    startDestination = "TimerScreen"
                ){
                    composable("LoginScreen"){
                        LoginScreen(navController = navController)
                    }
                    composable("LoginProcedure"){
                        LoginProcedure(navController = navController)
                    }
                    composable("RegisterProcedure"){
                        RegisterProcedure(navController = navController)
                    }
                    composable("HomeScreen"){
                        HomeScreen(navController = navController)
                    }
                    composable("UserProfileScreen") {
                        UserProfileScreen(navController = navController)
                    }
                    composable("ActivitiesScreen") {
                        ActivitiesScreen(navController = navController,activityViewModel)
                    }
                    composable("TimerScreen"){
                        TimerScreenContent(timerViewModel ,activityViewModel , navController)
                    }
                }
            }
        }
    }
}

