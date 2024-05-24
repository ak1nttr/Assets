package com.cse234.assets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cse234.assets.screens.HomeScreen
import com.cse234.assets.screens.LoginProcedure
import com.cse234.assets.screens.LoginScreen
import com.cse234.assets.screens.RegisterProcedure
import com.cse234.assets.screens.UserProfileScreen
import com.cse234.assets.ui.theme.AssetsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssetsTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "UserProfileScreen"
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
                }

            }
        }
    }
}

