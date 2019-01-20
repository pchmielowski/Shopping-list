package net.chmielowski.shoppinglist.item

import io.reactivex.Observable
import net.chmielowski.shoppinglist.ItemId
import net.chmielowski.shoppinglist.Name
import net.chmielowski.shoppinglist.Quantity
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.item.Item
import net.chmielowski.shoppinglist.item.ReadItemsParams

interface ItemRepository {

    fun observe(params: ReadItemsParams): Observable<List<Item>>

    suspend fun add(name: Name, quantity: Quantity, shop: ShopId)

    suspend fun setCompleted(item: ItemId, isCompleted: Boolean)

    suspend fun delete(item: ItemId)
}