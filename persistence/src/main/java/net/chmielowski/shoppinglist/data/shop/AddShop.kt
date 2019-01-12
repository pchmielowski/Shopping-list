package net.chmielowski.shoppinglist.data.shop

import dagger.Lazy

import net.chmielowski.shoppinglist.AddShopType
import net.chmielowski.shoppinglist.shop.AddShopParams
import net.chmielowski.shoppinglist.shop.AddShopResult
import javax.inject.Inject


class AddShop @Inject constructor(private val dao: Lazy<ShopDao>) : AddShopType {
    override suspend fun invoke(params: AddShopParams) =
        try {
            val id = dao.get().insert(params.toEntity())
            AddShopResult.Success(id)
        } catch (e: Exception) {
            AddShopResult.ShopAlreadyPresent
        }

    private fun AddShopParams.toEntity() =
        ShopEntity(
            name = name,
            color = color?.let { ColorEntity(it.first, it.second) },
            icon = icon
        )
}
