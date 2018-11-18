package net.chmielowski.shoppinglist.data.item

import androidx.room.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ItemEntity
import net.chmielowski.shoppinglist.shop.ShopWithItemsCount

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemEntity WHERE completed = 0 AND shop = :shopId")
    fun observeNonCompletedItems(shopId: Id): Observable<List<ItemEntity>>

    @Query("SELECT * FROM ItemEntity WHERE shop = :shopId ORDER BY completed")
    fun observeAllItems(shopId: Id): Observable<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ItemEntity)

    @Query("UPDATE ItemEntity SET completed = :completed WHERE id = :id")
    fun updateCompleted(id: Id, completed: Boolean)

    @Query("DELETE FROM ItemEntity WHERE id = :item")
    fun remove(item: Id)

    class Fake : ItemDao {
        val subject = BehaviorSubject.createDefault<List<ItemEntity>>(emptyList())

        override fun observeNonCompletedItems(shopId: Id) =
            subject.map { it.filter { item -> !item.completed } }!!

        override fun observeAllItems(shopId: Id) = subject

        override fun insert(entity: ItemEntity) {
            val stored = subject.value!!
            val id = stored.map { it.id!! }.max()?.plus(1) ?: 1
            subject.onNext(stored + entity.copy(id = id))
        }

        override fun updateCompleted(id: Id, completed: Boolean) {
            subject.onNext(subject.value!!.map { if (it.id == id) it.copy(completed = completed) else it })
        }

        override fun remove(item: Id) {
            subject.onNext(subject.value!!.filterNot { it.id == item })
        }

    }
}