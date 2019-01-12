package net.chmielowski.shoppinglist.data.shop

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import net.chmielowski.shoppinglist.ObserveShopsType
import net.chmielowski.shoppinglist.shop.Shop
import net.chmielowski.shoppinglist.shop.ShopColor
import net.chmielowski.shoppinglist.shop.ShopIcon



class ObserveShops(private val dao: ShopDao) : ObserveShopsType {
    override fun invoke(params: Unit) = Single.just(dao)
        .subscribeOn(Schedulers.io())!!
        .flatMapObservable(ShopDao::getAllWithUncompletedItemsCount)
        .map(this::toDomainModels)!!

    private fun toDomainModels(shops: List<ShopWithItemsCount>) =
        shops.map(ShopWithItemsCount::toShopColor)
}

private fun ShopWithItemsCount.toShopColor() =
    Shop(
        id,
        name,
        color.toShopColor(),
        ShopIcon(icon),
        itemsCount
    )

fun ColorEntity?.toShopColor() = this?.let {
    ShopColor(
        it.hue,
        it.saturation
    )
}
