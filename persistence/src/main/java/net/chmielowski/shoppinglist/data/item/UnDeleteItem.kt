package net.chmielowski.shoppinglist.data.item

import dagger.Lazy
import net.chmielowski.shoppinglist.DeleteType
import net.chmielowski.shoppinglist.Id
import javax.inject.Inject

class UnDeleteItem @Inject constructor(val dao: Lazy<ItemDao>) : DeleteType {

    override suspend fun invoke(params: Id) = dao.get().unDelete(params)
}
