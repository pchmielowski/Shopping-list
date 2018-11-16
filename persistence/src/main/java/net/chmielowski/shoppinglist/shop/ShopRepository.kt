package net.chmielowski.shoppinglist.shop

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import net.chmielowski.shoppinglist.Id

interface ShopRepository {
    fun observe(): Observable<List<ShopEntity>>

    fun add(entity: ShopEntity): Single<Id>

    class Fake : ShopRepository {
        val observe = PublishSubject.create<List<ShopEntity>>()
        override fun observe() = Observable.just<List<ShopEntity>>(
            listOf(
                ShopEntity("Dupa", null, 0).apply { id = 0 }
            )
        )

        val add = PublishSubject.create<Id>()
        override fun add(entity: ShopEntity) = add.firstOrError()!!
    }
}
