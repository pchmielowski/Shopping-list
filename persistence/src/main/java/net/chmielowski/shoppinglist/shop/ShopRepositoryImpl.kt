package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.IconId
import net.chmielowski.shoppinglist.Name
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.data.shop.*

class ShopRepositoryImpl(private val dao: ShopDao) : ShopRepository {

    override fun observe() =
        dao.getAllWithUncompletedItemsCount()
            .map(this::toDomainModels)!!

    private fun toDomainModels(shops: List<ShopWithItemsCount>) =
        shops.map(ShopWithItemsCount::toShopColor)


    override suspend fun getAppearance(shop: ShopId) =
        dao.findShopById(shop.value.toLong())
            .toShopAppearance()


    override suspend fun getAppearance(name: Name, color: ShopColor?, icon: IconId) =
        try {
            val id = dao.insert(
                ShopEntity(
                    name = name,
                    color = color?.let { ColorEntity(it.first, it.second) },
                    icon = icon.value.toLong()
                )
            )
            AddShopResult.Success(id)
        } catch (e: Exception) {
            AddShopResult.ShopAlreadyPresent
        }
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