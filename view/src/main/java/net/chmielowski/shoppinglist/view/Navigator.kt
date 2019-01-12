package net.chmielowski.shoppinglist.view

import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.view.shops.Strings


class Navigator(private val strings: Strings, private val activityProvider: MainActivity.Provider) {

    private val controller
        get() = activityProvider.instance.navController

    fun toItemList(shopId: ShopId) {
        controller.navigate(R.id.itemList, bundleOf(key(R.string.argument_shop_id) to shopId.value))
    }

    fun toAddShop() {
        controller.navigate(R.id.addShop)
    }

    private fun key(@StringRes int: Int) = strings.get(int)
}
