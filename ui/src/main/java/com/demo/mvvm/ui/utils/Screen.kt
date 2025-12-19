package com.demo.mvvm.ui.utils

import com.demo.mvvm.R


sealed class Screen(val route: String, var icon: Int? = null, var title: String? = null) {
    object PostsScreen : Screen("posts_screen", R.drawable.ic_chrome, "Posts")
}