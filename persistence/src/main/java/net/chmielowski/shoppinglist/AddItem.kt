package net.chmielowski.shoppinglist

import net.chmielowski.shoppinglist.data.item.ItemRepository
import net.chmielowski.shoppinglist.view.items.AddItemParams
import javax.inject.Inject

class AddItem @Inject constructor(private val repository: ItemRepository) : AddItemType {
    override fun invoke(params: AddItemParams) =
        repository.insert(ItemEntity(name = params.name, quantity = params.quantity?.toInt(), shop = params.shopId))
            .map { id -> Item(id, params.name, false, parseQuantity(params)) }!!

    private fun parseQuantity(params: AddItemParams) =
        params.quantity?.let { Item.Quantity.NoUnit(it.toInt()) }
}
