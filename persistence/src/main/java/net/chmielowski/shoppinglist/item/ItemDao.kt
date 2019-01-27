package net.chmielowski.shoppinglist.item

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import net.chmielowski.shoppinglist.ItemId
import net.chmielowski.shoppinglist.ShopId

@Dao
interface ItemDao {

    @Query("SELECT * FROM ItemEntity WHERE shop = :shopId AND completed = 0 AND deleted = 0")
    fun observeNonCompletedItems(shopId: ShopId): Observable<List<ItemEntity>>

    @Query("SELECT * FROM ItemEntity WHERE shop = :shopId AND deleted = 0 ORDER BY completed")
    fun observeAllItems(shopId: ShopId): Observable<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ItemEntity)

    @Query("UPDATE ItemEntity SET completed = :completed WHERE id = :id")
    suspend fun updateCompleted(id: ItemId, completed: Boolean)

    @Query("UPDATE ItemEntity SET deleted = 1 WHERE id = :item")
    suspend fun delete(item: ItemId)

}
