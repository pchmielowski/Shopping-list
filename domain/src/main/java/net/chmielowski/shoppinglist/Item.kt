package net.chmielowski.shoppinglist

data class Item(override val id: Id, val name: String) : HasId
