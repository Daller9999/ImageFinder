package com.testapp.coreui.navigation

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator

class NavigationManager(
    context: Context
) : NavHostController(context) {

    private val hashMapBundle = hashMapOf<String, Bundle>()
    private var currentRoute: String = ""

    fun navigate(route: String, bundle: Bundle) {
        hashMapBundle[route] = bundle
        currentRoute = route
        navigate(route)
    }

    fun getArgs(): Bundle? = hashMapBundle[currentRoute]

    override fun popBackStack(): Boolean {
        hashMapBundle.remove(currentRoute)
        return super.popBackStack()
    }
}

@Composable
fun rememberNavManager(
    vararg navigators: Navigator<out NavDestination>
): NavigationManager {
    val context = LocalContext.current
    return rememberSaveable(inputs = navigators, saver = NavControllerSaver(context)) {
        createNavController(context)
    }.apply {
        for (navigator in navigators) {
            navigatorProvider.addNavigator(navigator)
        }
    }
}


private fun NavControllerSaver(
    context: Context
): Saver<NavigationManager, *> = Saver(
    save = { it.saveState() },
    restore = { createNavController(context).apply { restoreState(it) } }
)

private fun createNavController(context: Context) =
    NavigationManager(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }