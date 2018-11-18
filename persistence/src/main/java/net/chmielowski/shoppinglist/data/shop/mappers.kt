package net.chmielowski.shoppinglist.data.shop

import net.chmielowski.shoppinglist.shop.ShopIcon
import net.chmielowski.shoppinglist.shop.ShopAppearance

fun ShopEntity.toShopAppearance() =
    ShopAppearance(
        name,
        color.toShopColor(),
        ShopIcon(icon)
    )