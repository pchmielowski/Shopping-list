package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.Id

data class AddShopParams(val name: String, val color: ShopColor?, val icon: Id)
