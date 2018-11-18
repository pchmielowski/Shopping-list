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

    @Query("SELECT name FROM ShopEntity WHERE id = :id")
    fun getName(id: Id): String

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(entity: ShopEntity): Id

    class Fake(initial: List<ShopWithItemsCount> = emptyList()) : ShopDao {
        val subject = BehaviorSubject.createDefault(initial)

        private var failNext = false

        fun failNextInsert() {
            failNext = true
        }

        override fun getAllWithUncompletedItemsCount() = subject

        override fun getName(id: Id) = subject.value!!.single { it.id == id }.name

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
    }
}
