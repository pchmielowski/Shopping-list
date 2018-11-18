package net.chmielowski.shoppinglist.data.shop

import io.reactivex.subjects.BehaviorSubject
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.shop.ShopEntity
import net.chmielowski.shoppinglist.shop.ShopWithItemsCount

class FakeShopDao(initial: List<ShopWithItemsCount>) :
    ShopDao {
    private val subject = BehaviorSubject.createDefault(initial)

    override fun getAllWithUncompletedItemsCount() = subject

    override fun getName(id: Id) = subject.value!!.single { it.id == id }.name

    override fun insert(entity: ShopEntity): Id {
        val stored = subject.value!!
        val id = stored.map { it.id }.max()!! + 1
        val new = ShopWithItemsCount(id, entity.name, entity.color, entity.icon, 0)
        subject.onNext(stored + new)
        return id
    }
}
