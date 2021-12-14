package com.highsteaks.highsteaksmultiplatform.android.ui.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavController
import com.highsteaks.highsteaksmultiplatform.android.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object Chat : Screen("chat", R.string.chat)
    object Notification : Screen("notifications", R.string.notifications)
    object Login : Screen("login", R.string.login)
    object CreatePost: Screen("create_post",R.string.create_post)
}
fun navigateBottomNavigation(navController: NavController,route:String){
    navController.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(navController.graph.startDestinationRoute!!) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}