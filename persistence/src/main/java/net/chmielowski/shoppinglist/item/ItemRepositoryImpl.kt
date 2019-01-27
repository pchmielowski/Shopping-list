package net.chmielowski.shoppinglist.item

import io.reactivex.Observable
import net.chmielowski.shoppinglist.ItemId
import net.chmielowski.shoppinglist.Name
import net.chmielowski.shoppinglist.Quantity
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.item.ReadItemsParams.All
import net.chmielowski.shoppinglist.item.ReadItemsParams.NonCompletedOnly

class ItemRepositoryImpl(private val dao: ItemDao) : ItemRepository {

    override fun observe(params: ReadItemsParams, shopId: ShopId) =
        when (params) {
            NonCompletedOnly -> dao.observeNonCompletedItems(shopId)
            All -> dao.observeAllItems(shopId)
        }.mapToDomainModels()

    private fun Observable<List<ItemEntity>>.mapToDomainModels() =
        map { list -> list.map { it.toDomainModel() } }!!

    private fun ItemEntity.toDomainModel() = Item(id!!, name, completed, quantity)

    override suspend fun add(name: Name, quantity: Quantity, shop: ShopId) =
        dao.insert(ItemEntity(name = name, quantity = quantity, shop = shop))

    override suspend fun setCompleted(item: ItemId, isCompleted: Boolean) =
        dao.updateCompleted(item, isCompleted)

    override suspend fun delete(item: ItemId) = dao.delete(item)
}