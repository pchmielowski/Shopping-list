package net.chmielowski.shoppinglist

data class Item(
    val id: Id,
    val name: String,
    val completed: Boolean,
    val quantity: String
)
