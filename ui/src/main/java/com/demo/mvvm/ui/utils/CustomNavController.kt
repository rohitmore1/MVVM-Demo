package com.demo.mvvm.ui.utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.mvvm.ui.features.login.ui.RenderPostsScreen
import com.demo.mvvm.ui.features.login.viewModel.PostsViewModel

@Composable
fun CustomNavController(
    activity: Activity?,
    postsViewModel: PostsViewModel,
    modifier: Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.PostsScreen.route
    ) {
        composable(Screen.PostsScreen.route) {
            // RenderLoginScreen(navController = navController, postsViewModel)
            RenderPostsScreen(navController = navController, postsViewModel)
        }
    }
}