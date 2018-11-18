package net.chmielowski.shoppinglist.data.shop

import dagger.Lazy
import net.chmielowski.shoppinglist.*
import javax.inject.Inject

class GetShopAppearance @Inject constructor(private val dao: Lazy<ShopDao>) : GerShopAppearance {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun invoke(id: Id) =
        dao.asSingle()
            .map { it.findShopById(id) }
            .map { it.toShopAppearance() }!!
}
