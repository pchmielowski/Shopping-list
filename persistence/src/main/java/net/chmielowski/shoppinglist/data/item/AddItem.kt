package net.chmielowski.shoppinglist.data.item

import dagger.Lazy

import net.chmielowski.shoppinglist.AddItemType
import net.chmielowski.shoppinglist.item.AddItemParams
import javax.inject.Inject


class AddItem @Inject constructor(private val dao: Lazy<ItemDao>) : AddItemType {
    override suspend fun invoke(params: AddItemParams) =
        dao.get().insert(params.toEntity())

    private fun AddItemParams.toEntity() =
        ItemEntity(
            name = name,
            quantity = quantity,
            shop = shopId
        )
}
