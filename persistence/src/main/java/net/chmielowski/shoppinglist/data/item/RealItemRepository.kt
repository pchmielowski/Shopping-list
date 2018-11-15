package net.chmielowski.shoppinglist.data.item

import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ItemEntity
import net.chmielowski.shoppinglist.mapCompletable
import javax.inject.Inject

class RealItemRepository @Inject constructor(private val _dao: Lazy<ItemDao>) :
    ItemRepository {
    private val dao: Single<ItemDao>
        get() = Single.fromCallable { _dao.get() }.subscribeOn(Schedulers.io())

    override fun findItems(completed: Boolean): Single<List<ItemEntity>> =
        dao.map { it.findItems(completed) }

    override fun insert(entity: ItemEntity): Single<Id> = dao.map { it.insert(entity) }

    override fun updateCompleted(id: Id, completed: Boolean): Completable =
        dao.mapCompletable { it.updateCompleted(id, completed) }
}