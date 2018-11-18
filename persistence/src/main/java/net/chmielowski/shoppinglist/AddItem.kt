package net.chmielowski.shoppinglist

import dagger.Lazy
import net.chmielowski.shoppinglist.data.item.ItemDao
import net.chmielowski.shoppinglist.view.items.AddItemParams
import javax.inject.Inject

class AddItem @Inject constructor(private val dao: Lazy<ItemDao>) : AddItemType {
    override fun invoke(params: AddItemParams) =
        dao.asSingle()
            .map { it.insert(params.toEntity()) }
            .ignoreElement()!!

    private fun AddItemParams.toEntity() =
        ItemEntity(
            name = name,
            quantity = quantity,
            shop = shopId
        )
}
