package net.chmielowski.shoppinglist

import io.reactivex.Single

class AddItemAction(private val dao: ItemDao) : DataAction<String, Item> {
    override fun invoke(@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE") name: String): Single<Item> {
        return dao.insert(ItemEntity(name = name))
            .map { id -> Item(id, name) }
    }
}
