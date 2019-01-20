package net.chmielowski.shoppinglist.shop

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.chmielowski.shoppinglist.Name
import net.chmielowski.shoppinglist.ShopId

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
    fun findShopById(id: ShopId): ShopEntity

    @Query("SELECT COUNT (*) FROM ShopEntity WHERE name = :name")
    fun countShopByName(name: Name): Int

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(entity: ShopEntity): Long

    @Query("DELETE FROM ShopEntity WHERE id = :shop")
    fun delete(shop: ShopId)

    class Fake(initial: List<ShopWithItemsCount> = emptyList()) :
        ShopDao {

        override fun countShopByName(name: Name) = TODO("not implemented")

        val subject = BehaviorSubject.createDefault(initial)

        private var failNext = false

        fun failNextInsert() {
            failNext = true
        }

        override fun getAllWithUncompletedItemsCount() = subject

        override fun findShopById(id: ShopId) = subject.value!!
            .single { it.id == id }
            .let { ShopEntity(it.id, it.name, it.color, it.icon) }

        override fun insert(entity: ShopEntity): Long {
            if (failNext) {
                failNext = false
                throw Exception()
            }
            val stored = subject.value!!
            val id = stored.map { it.id.value }.max()?.plus(1) ?: 1
            val new = ShopWithItemsCount(
                ShopId(id),
                entity.name,
                entity.color,
                entity.icon,
                0
            )
            subject.onNext(stored + new)
            return id.toLong()
        }

        override fun delete(shop: ShopId) = TODO("not implemented")
    }
}
