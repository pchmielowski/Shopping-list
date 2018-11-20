package net.chmielowski.shoppinglist.data.shop

import dagger.Lazy
import net.chmielowski.shoppinglist.GetShopAppearanceType
import net.chmielowski.shoppinglist.Id
import javax.inject.Inject

class GetShopAppearance @Inject constructor(private val dao: Lazy<ShopDao>) : GetShopAppearanceType {
    override suspend fun invoke(params: Id) =
        dao.get()
            .findShopById(params)
            .toShopAppearance()
}
