package net.chmielowski.shoppinglist.view

import androidx.navigation.NavDirections

class Navigator(private val activityProvider: MainActivity.Provider) {

    private val controller
        get() = activityProvider.instance.navController

    fun navigate(action: NavDirections) {
        controller.navigate(action)
    }
}
