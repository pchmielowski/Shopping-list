package net.chmielowski.shoppinglist.data.item

import dagger.Lazy
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ItemEntity
import net.chmielowski.shoppinglist.asSingle
import net.chmielowski.shoppinglist.mapCompletable
import javax.inject.Inject

class RealItemRepository @Inject constructor(private val dao: Lazy<ItemDao>) : ItemRepository {
    override fun observeItems(completed: Boolean, shopId: Id) =
        dao.asSingle().flatMapObservable { it.findItems(completed, shopId) }!!

    override fun insert(entity: ItemEntity) = dao.asSingle()
        .mapCompletable { it.insert(entity) }

    override fun updateCompleted(id: Id, completed: Boolean) =
        dao.asSingle().mapCompletable { it.updateCompleted(id, completed) }
}
