package net.chmielowski.shoppinglist.data.item

import dagger.Lazy
import net.chmielowski.shoppinglist.AddItemType
import net.chmielowski.shoppinglist.data.asSingle
import net.chmielowski.shoppinglist.item.AddItemParams
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
