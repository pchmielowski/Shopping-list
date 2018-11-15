package net.chmielowski.shoppinglist.shop

import io.reactivex.Observable
import net.chmielowski.shoppinglist.ObserveShopsType
import net.chmielowski.shoppinglist.Shop
import net.chmielowski.shoppinglist.ShopColor
import net.chmielowski.shoppinglist.ShopIcon
import net.chmielowski.shoppinglist.data.shop.ShopDao
import javax.inject.Inject

internal class ObserveShops @Inject constructor(private val dao: ShopDao) : ObserveShopsType {
    // TODO: repository
    override fun invoke(params: Unit) = Observable.fromCallable(dao::getAll)
        .map(this::toDomainModels)!!

    private fun toDomainModels(shops: List<ShopEntity>) =
        shops.map(ShopEntity::toDomainModel)
}

private fun ShopEntity.toDomainModel() =
    Shop(id!!, name, ShopColor(color.hue, color.saturation), ShopIcon(icon!!))
