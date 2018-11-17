package net.chmielowski.shoppinglist.data.item

import dagger.Lazy
import io.reactivex.Completable
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ItemEntity
import net.chmielowski.shoppinglist.asSingle
import net.chmielowski.shoppinglist.mapCompletable
import javax.inject.Inject

class RealItemRepository @Inject constructor(private val dao: Lazy<ItemDao>) :
    ItemRepository {
    override fun observeItems(completed: Boolean, shopId: Id) =
        dao.asSingle().flatMapObservable { it.findItems(completed, shopId) }!!

    override fun insert(entity: ItemEntity): Completable = dao.asSingle()
        .map { it.insert(entity) }
        .ignoreElement()

    override fun updateCompleted(id: Id, completed: Boolean): Completable =
        dao.asSingle().mapCompletable { it.updateCompleted(id, completed) }
}
