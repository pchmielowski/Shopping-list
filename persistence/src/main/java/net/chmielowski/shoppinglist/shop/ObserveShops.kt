package net.chmielowski.shoppinglist.shop

import dagger.Lazy
import net.chmielowski.shoppinglist.*
import net.chmielowski.shoppinglist.data.shop.ShopDao
import javax.inject.Inject

class ObserveShops @Inject constructor(private val dao: Lazy<ShopDao>) : ObserveShopsType {
    override fun invoke(params: Unit) = dao
        .asSingle()
        .flatMapObservable(ShopDao::getAllWithUncompletedItemsCount)
        .map(this::toDomainModels)!!

    private fun toDomainModels(shops: List<ShopWithItemsCount>) =
        shops.map(ShopWithItemsCount::toDomainModel)
}

private fun ShopWithItemsCount.toDomainModel() =
    Shop(id, name, color.toDomainModel(), ShopIcon(icon), itemsCount)

private fun ColorEntity?.toDomainModel() = this?.let { ShopColor(it.hue, it.saturation) }
