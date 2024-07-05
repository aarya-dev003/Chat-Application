package com.aarya.chatapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aarya.chatapplication.presentation.chat.ChatScreen
import com.aarya.chatapplication.presentation.username.UsernameScreen
import com.aarya.chatapplication.ui.theme.ChatApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatApplicationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "username_screen"
                    ){
                    composable("username_screen"){
                        UsernameScreen(onNavigate = navController::navigate)
                    }

                    composable(
                        route = "chat_screen/{username}",
                        arguments = listOf(
                            navArgument(name = "username"){
                                type = NavType.StringType
                                nullable =true
                            }
                        )
                    ){
                        val username = it.arguments?.getString("username")
                        ChatScreen(username = username)
                    }


                }
            }
        }
    }
}

