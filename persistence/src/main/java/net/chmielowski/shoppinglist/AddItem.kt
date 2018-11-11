package net.chmielowski.shoppinglist

import io.reactivex.Single
import net.chmielowski.shoppinglist.view.items.AddItemParams

class AddItem(private val dao: ItemDao) : ActionWithResult<AddItemParams, Item> {
    override fun invoke(@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE") params: AddItemParams): Single<Item> {
        return dao.insert(ItemEntity(name = params.name, quantity = params.quantity?.toInt()))
            .map { id -> Item(id, params.name, false, parseQuantity(params)) }
    }

    private fun parseQuantity(params: AddItemParams) =
        params.quantity?.let { Item.Quantity.NoUnit(it.toInt()) }
}
