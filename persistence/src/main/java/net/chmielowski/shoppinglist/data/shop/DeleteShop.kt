package net.chmielowski.shoppinglist.data.shop

import dagger.Lazy
import net.chmielowski.shoppinglist.DeleteType
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.data.asSingle
import javax.inject.Inject

class DeleteShop @Inject constructor(val dao: Lazy<ShopDao>) : DeleteType {
    override fun invoke(params: Id) = dao.asSingle()
        .map { it.delete(params) }
        .ignoreElement()!!
}
