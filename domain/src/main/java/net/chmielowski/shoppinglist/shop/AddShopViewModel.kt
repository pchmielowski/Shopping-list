package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.Id

sealed class AddShopResult {
    object ShopAlreadyPresent : AddShopResult()
    object Success : AddShopResult()
}

data class AddShopParams(val name: String, val color: Float, val icon: Id)