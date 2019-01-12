package net.chmielowski.shoppinglist.data.shop

import net.chmielowski.shoppinglist.GetShopAppearanceType
import net.chmielowski.shoppinglist.Id


class GetShopAppearance(private val dao: ShopDao) : GetShopAppearanceType {
    override suspend fun invoke(params: Id) =
        dao.findShopById(params)
            .toShopAppearance()
}
