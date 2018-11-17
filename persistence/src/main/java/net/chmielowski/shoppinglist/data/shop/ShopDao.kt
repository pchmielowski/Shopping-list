package net.chmielowski.shoppinglist.data.shop

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.shop.ShopEntity
import net.chmielowski.shoppinglist.shop.ShopWithItemsCount

@Dao
interface ShopDao {
    @Query(
        """
        SELECT ShopEntity.*, COUNT(*) AS itemsCount
        FROM ShopEntity
        INNER JOIN ItemEntity ON ItemEntity.shop == ShopEntity.id
        WHERE ItemEntity.completed = 0
        GROUP BY ShopEntity.id
        """
    )
    fun getAllWithUncompletedItemsCount(): Observable<List<ShopWithItemsCount>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(entity: ShopEntity): Id
}
