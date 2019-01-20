package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.ShopId

sealed class AddShopResult {

    object ShopAlreadyPresent : AddShopResult()

    data class Success(val id: ShopId) : AddShopResult()
}