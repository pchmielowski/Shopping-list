package net.chmielowski.shoppinglist.view

import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.shops.Strings
import javax.inject.Inject

class Navigator @Inject constructor(activity: MainActivity, val strings: Strings) {
    private val navController = activity.findNavController(R.id.my_nav_host_fragment)

    fun toItemList(shopId: Id) {
        navController.navigate(R.id.itemList, bundleOf(key(R.string.argument_shop_id) to shopId))
    }

    fun toAddShop() {
        navController.navigate(R.id.addShop)
    }

    private fun key(@StringRes int: Int) = strings.get(int)
}