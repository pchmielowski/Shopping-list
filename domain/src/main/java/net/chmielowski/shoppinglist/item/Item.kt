package net.chmielowski.shoppinglist.item

import net.chmielowski.shoppinglist.ItemId
import net.chmielowski.shoppinglist.Name

data class Item(
    val id: ItemId,
    val name: Name,
    val isCompleted: Boolean,
    val quantity: String
)
