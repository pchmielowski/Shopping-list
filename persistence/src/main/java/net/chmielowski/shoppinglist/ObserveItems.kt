package net.chmielowski.shoppinglist

import net.chmielowski.shoppinglist.data.item.ItemRepository
import javax.inject.Inject

class ObserveItems @Inject constructor(private val repository: ItemRepository) :
    ObserveItemsType {
    override fun invoke(params: ReadItemsParams) = repository.observeItems(params.showCompleted, params.shopId)
        .map { list -> list.map { it.toDomainModel() } }!!

    private fun ItemEntity.toDomainModel() = Item(id!!, name, completed, quantity)

}
