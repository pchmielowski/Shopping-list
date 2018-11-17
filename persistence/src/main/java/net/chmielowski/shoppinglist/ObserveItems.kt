package net.chmielowski.shoppinglist

import net.chmielowski.shoppinglist.data.item.ItemRepository
import javax.inject.Inject

class ObserveItems @Inject constructor(private val repository: ItemRepository) :
    ObserveItemsType {
    override fun invoke(params: ReadItemsParams) = repository.observeItems(params.showCompleted, params.shopId)
        .map { list -> list.map(this::toDomainModel) }!!

    private fun toDomainModel(entity: ItemEntity) = Item(entity.id!!, entity.name, entity.completed, null)

}
