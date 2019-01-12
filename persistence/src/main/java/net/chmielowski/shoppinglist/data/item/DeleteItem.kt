package net.chmielowski.shoppinglist.data.item

import net.chmielowski.shoppinglist.DeleteType
import net.chmielowski.shoppinglist.Id


class DeleteItem(val dao: ItemDao) : DeleteType {

    override suspend fun invoke(params: Id) = dao.delete(params)
}
