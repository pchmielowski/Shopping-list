package net.chmielowski.shoppinglist.view.items

import net.chmielowski.shoppinglist.Id

data class ItemViewModel(
    override val id: Id,
    val name: String,
    val completed: Boolean,
    val quantity: String
) : AdapterItemViewModel
