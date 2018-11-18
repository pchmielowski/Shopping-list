package net.chmielowski.shoppinglist.data.shop

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.shop.ShopEntity
import net.chmielowski.shoppinglist.shop.ShopWithItemsCount

@Dao
interface ShopDao {
    @Query(
        """
        SELECT ShopEntity.*, COUNT(shop) AS itemsCount
        FROM ShopEntity
        LEFT JOIN (SELECT shop FROM ItemEntity WHERE completed = 0) ON shop == ShopEntity.id
        GROUP BY ShopEntity.id
        """
    )
    fun getAllWithUncompletedItemsCount(): Observable<List<ShopWithItemsCount>>

    @Query("SELECT * FROM ShopEntity WHERE id = :id")
    fun findShopById(id: Id): ShopEntity

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(entity: ShopEntity): Id

    @Query("DELETE FROM ShopEntity WHERE id = :shop")
    fun delete(shop: Id)

    class Fake(initial: List<ShopWithItemsCount> = emptyList()) : ShopDao {
        val subject = BehaviorSubject.createDefault(initial)

        private var failNext = false

        fun failNextInsert() {
            failNext = true
        }

        override fun getAllWithUncompletedItemsCount() = subject

        override fun findShopById(id: Id) = subject.value!!
            .single { it.id == id }
            .let { ShopEntity(it.id, it.name, it.color, it.icon) }

        override fun insert(entity: ShopEntity): Id {
            if (failNext) {
                failNext = false
                throw Exception()
            }
            val stored = subject.value!!
            val id = stored.map { it.id }.max()?.plus(1) ?: 1
            val new = ShopWithItemsCount(id, entity.name, entity.color, entity.icon, 0)
            subject.onNext(stored + new)
            return id
        }

        override fun delete(shop: Id) = TODO("not implemented")
    }
}
