package net.chmielowski.shoppinglist

import io.reactivex.Single

class ReadItemsAction(private val dao: ItemDao) : ReadAction<ReadItemsAction.Params, List<Item>> {
    override fun invoke(params: Params): Single<List<Item>> = dao.findItems()
        .map { list -> list.map(this::toDomainModel) }

    private fun toDomainModel(it: ItemEntity) = Item(it.id, it.name)

    object Params : ReadAction.Params
}
