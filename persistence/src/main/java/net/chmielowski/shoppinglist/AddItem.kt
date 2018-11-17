package net.chmielowski.shoppinglist

import net.chmielowski.shoppinglist.data.item.ItemRepository
import net.chmielowski.shoppinglist.view.items.AddItemParams
import javax.inject.Inject

class AddItem @Inject constructor(private val repository: ItemRepository) : AddItemType {
    override fun invoke(params: AddItemParams) =
        repository.insert(params.toEntity())

    private fun AddItemParams.toEntity() =
        ItemEntity(
            name = name,
            quantity = quantity,
            shop = shopId
        )
}
