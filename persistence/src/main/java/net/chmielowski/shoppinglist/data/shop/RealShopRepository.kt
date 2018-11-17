package net.chmielowski.shoppinglist.data.shop

import dagger.Lazy
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.asSingle
import net.chmielowski.shoppinglist.shop.ShopEntity
import net.chmielowski.shoppinglist.shop.ShopRepository
import javax.inject.Inject

class RealShopRepository @Inject constructor(private val dao: Lazy<ShopDao>) : ShopRepository {
    override fun getName(id: Id) = dao.asSingle().map { it.getName(id) }!!

    override fun observe() = dao.get().getAllWithUncompletedItemsCount()

    override fun add(entity: ShopEntity) = dao.asSingle()
        .map { it.insert(entity) }!!
}