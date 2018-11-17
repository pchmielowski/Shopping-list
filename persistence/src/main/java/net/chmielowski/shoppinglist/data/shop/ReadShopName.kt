package net.chmielowski.shoppinglist.data.shop

import net.chmielowski.shoppinglist.ReadShopNameType
import net.chmielowski.shoppinglist.shop.ReadShopNameParams
import net.chmielowski.shoppinglist.shop.ShopRepository
import javax.inject.Inject

class ReadShopName @Inject constructor(private val repository: ShopRepository) : ReadShopNameType {
    override fun invoke(params: ReadShopNameParams) = repository.getName(params.id)
}
