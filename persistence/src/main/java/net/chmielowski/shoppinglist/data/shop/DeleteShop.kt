package net.chmielowski.shoppinglist.data.shop

import net.chmielowski.shoppinglist.DeleteType
import net.chmielowski.shoppinglist.Id


class DeleteShop(val dao: ShopDao) : DeleteType {
    override suspend fun invoke(params: Id) = dao.delete(params)
}
