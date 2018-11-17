package net.chmielowski.shoppinglist.data.shop

import dagger.Lazy
import net.chmielowski.shoppinglist.asSingle
import net.chmielowski.shoppinglist.shop.ShopEntity
import net.chmielowski.shoppinglist.shop.ShopRepository
import javax.inject.Inject

class RealShopRepository @Inject constructor(private val dao: Lazy<ShopDao>) :
    ShopRepository {
    override fun observe() = dao.get().getAllWithUncompletedItemsCount()

    override fun add(entity: ShopEntity) = dao.asSingle()
        .map { it.insert(entity) }
}