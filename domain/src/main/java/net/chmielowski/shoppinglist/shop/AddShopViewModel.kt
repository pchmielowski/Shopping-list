package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ShopColor

sealed class AddShopResult {
    object ShopAlreadyPresent : AddShopResult()
    data class Success(val id: Id) : AddShopResult()
}

data class AddShopParams(val name: String, val color: ShopColor?, val icon: Id)
