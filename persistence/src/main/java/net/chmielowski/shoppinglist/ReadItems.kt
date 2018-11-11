package net.chmielowski.shoppinglist

import io.reactivex.Single
import javax.inject.Inject

class ReadItems @Inject constructor(private val dao: ItemDao) : ActionWithResult<ReadItemsParams, List<@JvmSuppressWildcards Item>> {
    override fun invoke(params: ReadItemsParams): Single<List<Item>> = dao.findItems(params.showCompleted)
        .map { list -> list.map(this::toDomainModel) }

    private fun toDomainModel(entity: ItemEntity) = Item(entity.id!!, entity.name, entity.completed, null)

}
