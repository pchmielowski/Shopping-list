package net.chmielowski.shoppinglist.item

import net.chmielowski.shoppinglist.ItemId

data class Item(
    val id: ItemId,
    val name: String,
    val completed: Boolean,
    val quantity: String
)
