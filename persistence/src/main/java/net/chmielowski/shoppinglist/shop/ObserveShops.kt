package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.ObserveShopsType
import net.chmielowski.shoppinglist.Shop
import net.chmielowski.shoppinglist.ShopColor
import net.chmielowski.shoppinglist.ShopIcon
import javax.inject.Inject

class ObserveShops @Inject constructor(private val repo: ShopRepository) : ObserveShopsType {
    override fun invoke(params: Unit) = repo.observe()
        .map(this::toDomainModels)!!

    private fun toDomainModels(shops: List<ShopEntity>) =
        shops.map(ShopEntity::toDomainModel)
}

private fun ShopEntity.toDomainModel() =
    Shop(id!!, name, color.toDomainModel(), ShopIcon(icon))

private fun ColorEntity?.toDomainModel() = this?.let { ShopColor(it.hue, it.saturation) }
