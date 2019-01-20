package net.chmielowski.shoppinglist.item

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.chmielowski.shoppinglist.ItemId
import net.chmielowski.shoppinglist.ShopId

@Dao
interface ItemDao {

    @Query("SELECT * FROM ItemEntity WHERE shop = :shopId AND completed = 0 AND deleted = 0")
    fun observeNonCompletedItems(shopId: ShopId): Observable<List<ItemEntity>>

    @Query("SELECT * FROM ItemEntity WHERE shop = :shopId AND deleted = 0 ORDER BY completed")
    fun observeAllItems(shopId: ShopId): Observable<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ItemEntity)

    @Query("UPDATE ItemEntity SET completed = :completed WHERE id = :id")
    fun updateCompleted(id: ItemId, completed: Boolean)

    @Query("UPDATE ItemEntity SET deleted = 1 WHERE id = :item")
    fun delete(item: ItemId)

    class Fake : ItemDao {

        val subject = BehaviorSubject.createDefault<List<ItemEntity>>(emptyList())

        override fun observeNonCompletedItems(shopId: ShopId) =
            subject.map { it.filter { item -> !item.completed } }!!

        override fun observeAllItems(shopId: ShopId) = subject

        override fun insert(entity: ItemEntity) {
            val stored = subject.value!!
            val id = stored.map { it.id!!.value }.max()?.plus(1) ?: 1
            subject.onNext(stored + entity.copy(id = ItemId(id)))
        }

        override fun updateCompleted(id: ItemId, completed: Boolean) {
            subject.onNext(subject.value!!.map { if (it.id == id) it.copy(completed = completed) else it })
        }

        override fun delete(item: ItemId) {
            subject.onNext(subject.value!!.filterNot { it.id == item })
        }

    }
}