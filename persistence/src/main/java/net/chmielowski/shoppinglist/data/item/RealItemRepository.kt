package net.chmielowski.shoppinglist.data.item

import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Single
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ItemEntity
import net.chmielowski.shoppinglist.asSingle
import net.chmielowski.shoppinglist.mapCompletable
import javax.inject.Inject

class RealItemRepository @Inject constructor(private val dao: Lazy<ItemDao>) :
    ItemRepository {
    override fun observeItems(completed: Boolean) =
        dao.asSingle().flatMapObservable { it.findItems(completed) }!!

    override fun insert(entity: ItemEntity): Single<Id> = dao.asSingle().map { it.insert(entity) }

    override fun updateCompleted(id: Id, completed: Boolean): Completable =
        dao.asSingle().mapCompletable { it.updateCompleted(id, completed) }
}
