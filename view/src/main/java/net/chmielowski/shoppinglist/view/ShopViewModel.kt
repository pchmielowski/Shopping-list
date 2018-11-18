package net.chmielowski.shoppinglist.view

import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.shop.ShopColor

data class ShopViewModel(
    override val id: Id,
    val appearance: Appearance,
    val itemsNumber: Int
) : HasId {
    data class Appearance(
        val name: String,
        val color: ShopColor?,
        val icon: Int
    )
}