package net.chmielowski.shoppinglist.view.items

import net.chmielowski.shoppinglist.ItemId

data class ItemViewModel(
    override val id: ItemId,
    val name: String,
    val completed: Boolean,
    val quantity: String
) : AdapterItemViewModel<ItemId>
