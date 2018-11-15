package net.chmielowski.shoppinglist.data.item

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ItemEntity

interface ItemRepository {
    fun findItems(completed: Boolean): Single<List<ItemEntity>>

    fun insert(entity: ItemEntity): Single<Id>

    fun updateCompleted(id: Id, completed: Boolean): Completable

    class Fake : ItemRepository {
        val update = PublishSubject.create<Unit>()
        override fun updateCompleted(id: Id, completed: Boolean) = update.firstOrError().ignoreElement()!!

        val select = PublishSubject.create<List<ItemEntity>>()
        override fun findItems(completed: Boolean): Single<List<ItemEntity>> = select.firstOrError()

        val insert = PublishSubject.create<Id>()
        override fun insert(entity: ItemEntity) = insert.firstOrError()!!
    }
}