package net.chmielowski.shoppinglist.data.shop

import net.chmielowski.shoppinglist.ShopIcon
import net.chmielowski.shoppinglist.shop.ShopAppearance
import net.chmielowski.shoppinglist.shop.ShopEntity
import net.chmielowski.shoppinglist.shop.toShopColor

fun ShopEntity.toShopAppearance() =
    ShopAppearance(
        name,
        color.toShopColor(),
        ShopIcon(icon)
    )