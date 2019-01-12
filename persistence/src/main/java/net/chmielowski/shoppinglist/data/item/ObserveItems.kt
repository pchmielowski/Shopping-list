package net.chmielowski.shoppinglist.data.item

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import net.chmielowski.shoppinglist.ObserveItemsType
import net.chmielowski.shoppinglist.item.All
import net.chmielowski.shoppinglist.item.Item
import net.chmielowski.shoppinglist.item.NonCompletedOnly
import net.chmielowski.shoppinglist.item.ReadItemsParams


class ObserveItems(private val dao: ItemDao) : ObserveItemsType {
    override fun invoke(params: ReadItemsParams) =
        when (params) {
            is NonCompletedOnly -> observe { it.observeNonCompletedItems(params.shopId) }
            is All -> observe { it.observeAllItems(params.shopId) }
        }.mapToDomainModels()

    private fun observe(query: (ItemDao) -> Observable<List<ItemEntity>>) =
        Single.just(dao)
            .subscribeOn(Schedulers.io())!!.flatMapObservable(query)

    private fun Observable<List<ItemEntity>>.mapToDomainModels() =
        map { list -> list.map { it.toDomainModel() } }!!

    private fun ItemEntity.toDomainModel() = Item(id!!, name, completed, quantity)

}
