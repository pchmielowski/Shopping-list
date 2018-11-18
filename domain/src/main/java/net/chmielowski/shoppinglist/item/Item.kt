package net.chmielowski.shoppinglist.item

import net.chmielowski.shoppinglist.Id

data class Item(
    val id: Id,
    val name: String,
    val completed: Boolean,
    val quantity: String
)
