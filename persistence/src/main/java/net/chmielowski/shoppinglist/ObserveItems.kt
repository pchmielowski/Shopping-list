package net.chmielowski.shoppinglist

import io.reactivex.Observable
import net.chmielowski.shoppinglist.data.item.ItemRepository
import javax.inject.Inject

class ObserveItems @Inject constructor(private val repository: ItemRepository) : ObserveItemsType {
    override fun invoke(params: ReadItemsParams) =
        when (params) {
            is NonCompletedOnly -> repository.observeCompletedItems(params.shopId)
            is All -> repository.observeAllItems(params.shopId)
        }.mapToDomainModels()

    private fun Observable<List<ItemEntity>>.mapToDomainModels() =
        map { list -> list.map { it.toDomainModel() } }!!

    private fun ItemEntity.toDomainModel() = Item(id!!, name, completed, quantity)

}
