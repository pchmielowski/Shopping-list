package net.chmielowski.shoppinglist.shop

import io.reactivex.Observable
import net.chmielowski.shoppinglist.IconId
import net.chmielowski.shoppinglist.Name
import net.chmielowski.shoppinglist.ShopId

interface ShopRepository {

    fun observe(): Observable<List<Shop>>

    suspend fun getAppearance(shop: ShopId): ShopAppearance

    suspend fun getAppearance(name: Name, color: ShopColor?, icon: IconId): AddShopResult
}