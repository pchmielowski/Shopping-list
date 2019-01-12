package net.chmielowski.shoppinglist.view

import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.view.shops.Strings


class Navigator(
    private val strings: Strings,
    private val controller: NavController
) {
    fun toItemList(shopId: ShopId) {
        @Suppress("ConstantConditionIf")
        val destination = if (FeatureToggle.useNewItemListFragment)
            R.id.itemListNew
        else
            R.id.itemList
        controller.navigate(destination, bundleOf(key(R.string.argument_shop_id) to shopId))
    }

    fun toAddShop() {
        controller.navigate(R.id.addShop)
    }

    private fun key(@StringRes int: Int) = strings.get(int)
}
