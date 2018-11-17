package net.chmielowski.shoppinglist

import net.chmielowski.shoppinglist.data.item.ItemRepository
import javax.inject.Inject

class ReadItems @Inject constructor(private val repository: ItemRepository) : ReadItemsType {
    override fun invoke(params: ReadItemsParams) = repository.findItems(params.showCompleted)
        .map { list -> list.map(this::toDomainModel) }!!

    private fun toDomainModel(entity: ItemEntity) = Item(entity.id!!, entity.name, entity.completed, null)

}
