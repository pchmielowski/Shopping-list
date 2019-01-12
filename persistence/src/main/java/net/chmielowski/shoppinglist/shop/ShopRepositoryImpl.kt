package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.IconId
import net.chmielowski.shoppinglist.Name
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.data.shop.ColorEntity
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.data.shop.ShopEntity
import net.chmielowski.shoppinglist.data.shop.ShopWithItemsCount

class ShopRepositoryImpl(private val dao: ShopDao) : ShopRepository {

    override fun observe() =
        dao.getAllWithUncompletedItemsCount()
            .map(this::toDomainModels)!!

    private fun toDomainModels(shops: List<ShopWithItemsCount>) =
        shops.map(ShopWithItemsCount::toShopColor)


    override suspend fun getAppearance(shop: ShopId) =
        dao.findShopById(shop)
            .run {
                ShopAppearance(name, color.toShopColor(), ShopIcon(icon))
            }


    override suspend fun add(name: Name, color: ShopColor?, icon: IconId) =
        try {
            val id = dao.insert(entity(name, color, icon))
            AddShopResult.Success(ShopId(id.toInt()))
        } catch (e: Exception) {
            AddShopResult.ShopAlreadyPresent
        }

    private fun entity(name: Name, color: ShopColor?, icon: IconId) =
        ShopEntity(
            name = name,
            color = color?.let { ColorEntity(it.first, it.second) },
            icon = icon
        )
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