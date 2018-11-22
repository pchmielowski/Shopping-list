package net.chmielowski.shoppinglist.data.item

import dagger.Lazy
import io.reactivex.Observable
import net.chmielowski.bindinterface.BindInterface
import net.chmielowski.shoppinglist.ObserveItemsType
import net.chmielowski.shoppinglist.data.asSingle
import net.chmielowski.shoppinglist.item.All
import net.chmielowski.shoppinglist.item.Item
import net.chmielowski.shoppinglist.item.NonCompletedOnly
import net.chmielowski.shoppinglist.item.ReadItemsParams
import javax.inject.Inject

@BindInterface
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
