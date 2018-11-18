package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.ShopColor
import net.chmielowski.shoppinglist.ShopIcon

data class ShopAppearance(
    val name: String,
    val color: ShopColor?,
    val icon: ShopIcon
)
