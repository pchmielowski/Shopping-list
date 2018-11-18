package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.Id

sealed class AddShopResult {
    object ShopAlreadyPresent : AddShopResult()
    data class Success(val id: Id) : AddShopResult()
}