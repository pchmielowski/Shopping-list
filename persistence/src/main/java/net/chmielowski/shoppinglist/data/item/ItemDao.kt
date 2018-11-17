package net.chmielowski.shoppinglist.data.item

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ItemEntity

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemEntity WHERE completed = 0 AND shop = :shopId")
    fun observeNonCompletedItems(shopId: Id): Observable<List<ItemEntity>>

    @Query("SELECT * FROM ItemEntity WHERE shop = :shopId")
    fun observeAllItems(shopId: Id): Observable<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ItemEntity)

    @Query("UPDATE ItemEntity SET completed = :completed WHERE id = :id")
    fun updateCompleted(id: Id, completed: Boolean)
}