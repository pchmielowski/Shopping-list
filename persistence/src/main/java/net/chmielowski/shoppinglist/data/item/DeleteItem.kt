package net.chmielowski.shoppinglist.data.item

import dagger.Lazy
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.DeleteType
import net.chmielowski.shoppinglist.asSingle
import javax.inject.Inject

class DeleteItem @Inject constructor(val dao: Lazy<ItemDao>) : DeleteType {
    override fun invoke(item: Id) =
        dao.asSingle()
            .map { it.remove(item) }
            .ignoreElement()!!
}
