package net.chmielowski.shoppinglist.data.shop

import dagger.Lazy
import net.chmielowski.shoppinglist.ReadShopNameType
import net.chmielowski.shoppinglist.asSingle
import net.chmielowski.shoppinglist.shop.ReadShopNameParams
import javax.inject.Inject

class ReadShopName @Inject constructor(private val dao: Lazy<ShopDao>) : ReadShopNameType {
    override fun invoke(params: ReadShopNameParams) =
        dao.asSingle()
            .map { it.getName(params.id) }!!
}
