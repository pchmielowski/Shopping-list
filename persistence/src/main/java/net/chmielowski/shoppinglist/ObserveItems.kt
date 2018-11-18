package net.chmielowski.shoppinglist

import dagger.Lazy
import io.reactivex.Observable
import net.chmielowski.shoppinglist.data.item.ItemDao
import javax.inject.Inject

class ObserveItems @Inject constructor(private val dao: Lazy<ItemDao>) : ObserveItemsType {
    override fun invoke(params: ReadItemsParams) =
        when (params) {
            is NonCompletedOnly -> observe { it.observeNonCompletedItems(params.shopId) }
            is All -> observe { it.observeAllItems(params.shopId) }
        }.mapToDomainModels()

    private fun observe(query: (ItemDao) -> Observable<List<ItemEntity>>) =
        dao.asSingle().flatMapObservable(query)

    private fun Observable<List<ItemEntity>>.mapToDomainModels() =
        map { list -> list.map { it.toDomainModel() } }!!

    private fun ItemEntity.toDomainModel() = Item(id!!, name, completed, quantity)

}
