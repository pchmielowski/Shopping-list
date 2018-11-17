package net.chmielowski.shoppinglist.data.item

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ItemEntity

interface ItemRepository {
    fun observeItems(completed: Boolean): Observable<List<ItemEntity>>

    fun insert(entity: ItemEntity): Single<Id>

    fun updateCompleted(id: Id, completed: Boolean): Completable

    class Fake : ItemRepository {
        val update = PublishSubject.create<Unit>()
        override fun updateCompleted(id: Id, completed: Boolean) = update.firstOrError().ignoreElement()!!

        val observe = PublishSubject.create<List<ItemEntity>>()
        override fun observeItems(completed: Boolean) = observe

        val insert = PublishSubject.create<Id>()
        override fun insert(entity: ItemEntity) = insert.firstOrError()!!
    }
}