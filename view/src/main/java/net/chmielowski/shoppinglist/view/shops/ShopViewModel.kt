package net.chmielowski.shoppinglist.view.shops

import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.ShopId

data class ShopViewModel(
    override val id: ShopId,
    val itemsCount: String,
    val appearance: Appearance
) : HasId<ShopId> {
    data class Appearance(
        val name: String,
        val icon: Int,
        val colorVisible: Boolean,
        val color: Int?
    )
}
