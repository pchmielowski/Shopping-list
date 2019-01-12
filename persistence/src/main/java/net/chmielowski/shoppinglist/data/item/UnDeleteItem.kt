package net.chmielowski.shoppinglist.data.item

import net.chmielowski.shoppinglist.DeleteType
import net.chmielowski.shoppinglist.Id


class UnDeleteItem(val dao: ItemDao) : DeleteType {

    override suspend fun invoke(params: Id) = dao.unDelete(params)
}
