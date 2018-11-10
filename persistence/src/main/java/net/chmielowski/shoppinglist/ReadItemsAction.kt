package net.chmielowski.shoppinglist

import io.reactivex.Single

class ReadItemsAction(private val dao: ItemDao) : DataAction<ReadItemsParams, List<Item>> {
    override fun invoke(params: ReadItemsParams): Single<List<Item>> = dao.findItems(params.showCompleted)
        .map { list -> list.map(this::toDomainModel) }

    private fun toDomainModel(it: ItemEntity) = Item(it.id!!, it.name)

}
