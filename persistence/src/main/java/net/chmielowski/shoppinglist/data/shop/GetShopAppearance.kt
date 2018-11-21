package net.chmielowski.shoppinglist.data.shop

import dagger.Lazy
import net.chmielowski.bindinterface.BindInterface
import net.chmielowski.shoppinglist.GetShopAppearanceType
import net.chmielowski.shoppinglist.Id
import javax.inject.Inject

@BindInterface
class GetShopAppearance @Inject constructor(private val dao: Lazy<ShopDao>) : GetShopAppearanceType {
    override suspend fun invoke(params: Id) =
        dao.get()
            .findShopById(params)
            .toShopAppearance()
}

interface I
@BindInterface(
    qualifiers = ["net.chmielowski.shoppinglist.ShopQualifier",
        "net.chmielowski.shoppinglist.ItemQualifier"]
)
class C : I