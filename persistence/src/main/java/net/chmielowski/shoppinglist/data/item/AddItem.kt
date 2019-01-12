package net.chmielowski.shoppinglist.data.item

import net.chmielowski.shoppinglist.AddItemType
import net.chmielowski.shoppinglist.item.AddItemParams



class AddItem(private val dao: ItemDao) : AddItemType {
    override suspend fun invoke(params: AddItemParams) =
        dao.insert(params.toEntity())

    private fun AddItemParams.toEntity() =
        ItemEntity(
            name = name,
            quantity = quantity,
            shop = shopId
        )
}
