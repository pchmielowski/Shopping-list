package net.chmielowski.shoppinglist.shop

import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import net.chmielowski.shoppinglist.Id

interface ShopRepository {
    fun add(entity: ShopEntity): Single<Id>

    class Fake : ShopRepository {
        val add = PublishSubject.create<Id>()
        override fun add(entity: ShopEntity) = add.firstOrError()!!
    }
}
