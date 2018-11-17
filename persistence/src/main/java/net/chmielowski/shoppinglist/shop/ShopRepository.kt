package net.chmielowski.shoppinglist.shop

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import net.chmielowski.shoppinglist.Id

interface ShopRepository {
    fun observe(): Observable<List<ShopWithItemsCount>>

    fun add(entity: ShopEntity): Single<Id>

    class Fake : ShopRepository {
        val observe = PublishSubject.create<List<ShopWithItemsCount>>()
        override fun observe() = observe

        val add = PublishSubject.create<Id>()
        override fun add(entity: ShopEntity) = add.firstOrError()!!
    }
}
