package net.chmielowski.shoppinglist

import io.reactivex.Completable

class AddItemAction(private val dao: ItemDao) : WriteAction<String> {
    override fun invoke(@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE") name: String): Completable {
        return dao.insert(ItemEntity(name = name))
    }
}
