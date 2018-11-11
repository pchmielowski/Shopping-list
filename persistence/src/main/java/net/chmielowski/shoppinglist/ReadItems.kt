package net.chmielowski.shoppinglist

import io.reactivex.Single

class ReadItems(private val dao: ItemDao) : ActionWithResult<ReadItemsParams, List<Item>> {
    override fun invoke(params: ReadItemsParams): Single<List<Item>> = dao.findItems(params.showCompleted)
        .map { list -> list.map(this::toDomainModel) }

    private fun toDomainModel(entity: ItemEntity) = Item(entity.id!!, entity.name, entity.completed, null)

}
