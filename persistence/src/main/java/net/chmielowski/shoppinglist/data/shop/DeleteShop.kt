package net.chmielowski.shoppinglist.data.shop

import dagger.Lazy
import net.chmielowski.shoppinglist.DeleteType
import net.chmielowski.shoppinglist.Id
import javax.inject.Inject

class DeleteShop @Inject constructor(val dao: Lazy<ShopDao>) : DeleteType {
    override suspend fun invoke(params: Id) = dao.get().delete(params)
}
