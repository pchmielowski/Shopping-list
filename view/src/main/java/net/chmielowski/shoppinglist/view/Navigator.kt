package net.chmielowski.shoppinglist.view

import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.view.addshop.AddShopFragment
import net.chmielowski.shoppinglist.view.addshop.AddShopFragmentDirections
import net.chmielowski.shoppinglist.view.shops.Strings

class Navigator(private val strings: Strings, private val activityProvider: MainActivity.Provider) {

    private val controller
        get() = activityProvider.instance.navController

    fun toItemList(action: NavDirections) {
        controller.navigate(action)
    }

    private fun key(@StringRes int: Int) = strings.get(int)
}
