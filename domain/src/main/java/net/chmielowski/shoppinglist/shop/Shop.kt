package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.Id

typealias ShopColor = Pair<Int, Int>

data class ShopIcon(val id: Id)

data class Shop(
    val id: Id,
    val name: String,
    val color: ShopColor?,
    val icon: ShopIcon,
    val itemsCount: Int
)
