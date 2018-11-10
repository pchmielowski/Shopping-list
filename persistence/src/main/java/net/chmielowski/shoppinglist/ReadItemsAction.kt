package net.chmielowski.shoppinglist

import io.reactivex.Single

class ReadItemsAction(private val dao: ItemDao) : ReadAction<ReadItemsParams, List<Item>> {
    override fun invoke(params: ReadItemsParams): Single<List<Item>> = dao.findItems()
        .map { list -> list.map(this::toDomainModel) }

    private fun toDomainModel(it: ItemEntity) = Item(it.id!!, it.name)

}
