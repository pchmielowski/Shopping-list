package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.ActionWithResult
import net.chmielowski.shoppinglist.data.shop.ShopDao
import javax.inject.Inject
import dagger.Lazy
import net.chmielowski.shoppinglist.*

class AddShop @Inject constructor(private val lazyDao: Lazy<ShopDao>) : ActionWithResult<AddShopParams, AddShopResult> {
    override fun invoke(params: AddShopParams) =
        lazyDao.asSingle()
            .map { dao -> dao.insert(params.toEntity()) }
            .map<AddShopResult> { id -> AddShopResult.Success(id) }
            .onErrorReturn { AddShopResult.ShopAlreadyPresent }!!

    private fun AddShopParams.toEntity() =
        ShopEntity(name = name, color = color?.let { ColorEntity(it.first, it.second) }, icon = icon)
}
