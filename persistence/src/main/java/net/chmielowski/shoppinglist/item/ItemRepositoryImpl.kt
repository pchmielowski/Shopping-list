package net.chmielowski.shoppinglist.item

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import net.chmielowski.shoppinglist.ItemId
import net.chmielowski.shoppinglist.Name
import net.chmielowski.shoppinglist.Quantity
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.data.item.ItemDao
import net.chmielowski.shoppinglist.data.item.ItemEntity
import net.chmielowski.shoppinglist.shop.ItemRepository

class ItemRepositoryImpl(private val dao: ItemDao) : ItemRepository {

    override fun observe(params: ReadItemsParams) =
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

    override suspend fun add(name: Name, quantity: Quantity, shop: ShopId) =
        dao.insert(ItemEntity(name = name, quantity = quantity, shop = shop))

    override suspend fun setCompleted(item: ItemId, isCompleted: Boolean) =
        dao.updateCompleted(item, isCompleted)

    override suspend fun delete(item: ItemId) = dao.delete(item)
}