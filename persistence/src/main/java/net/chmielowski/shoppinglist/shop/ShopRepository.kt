package net.chmielowski.shoppinglist.shop

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import net.chmielowski.shoppinglist.Id

@Deprecated("Do not use")
interface ShopRepository {
    fun observe(): Observable<List<ShopWithItemsCount>>

    fun add(entity: ShopEntity): Single<Id>

    fun getName(id: Id): Single<String>

    @Deprecated("Do not use")
    class Fake : ShopRepository {
        override fun getName(id: Id) = TODO("not implemented")

        val observe = PublishSubject.create<List<ShopWithItemsCount>>()
        override fun observe() = observe

        val add = PublishSubject.create<Id>()
        override fun add(entity: ShopEntity) = add.firstOrError()!!
    }
}
