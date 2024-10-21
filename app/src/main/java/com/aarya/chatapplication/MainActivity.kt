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
import org.koin.androidx.compose.koinViewModel
import androidx.lifecycle.SavedStateHandle
import com.aarya.chatapplication.presentation.chat.ChatViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatApplicationTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "username_screen"
                ) {
                    composable("username_screen") {
                        UsernameScreen(onNavigate = navController::navigate)
                    }

                    composable(
                        route = "chat_screen/{username}",
                        arguments = listOf(
                            navArgument(name = "username") {
                                type = NavType.StringType
                                nullable = true
                            }
                        )
                    ) { backStackEntry ->
                        val username = backStackEntry.arguments?.getString("username")
                        // Get the SavedStateHandle from the backStackEntry
                        val savedStateHandle: SavedStateHandle = backStackEntry.savedStateHandle
                        // Pass the SavedStateHandle to the ViewModel
                        val chatViewModel: ChatViewModel = koinViewModel(parameters = { parametersOf(savedStateHandle) })
                        ChatScreen(username = username, viewModel = chatViewModel)
                    }
                }
            }
        }
    }
}
