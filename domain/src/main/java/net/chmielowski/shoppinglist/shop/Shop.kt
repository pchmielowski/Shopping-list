package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.IconId
import net.chmielowski.shoppinglist.ShopId

typealias ShopColor = Pair<Int, Int>

data class ShopIcon(val id: IconId)

data class Shop(
    val id: ShopId,
    val name: String,
    val color: ShopColor?,
    val icon: ShopIcon,
    val itemsCount: Int
)
