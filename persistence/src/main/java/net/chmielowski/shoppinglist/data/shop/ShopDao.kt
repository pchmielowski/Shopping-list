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
        SELECT ShopEntity.*, COUNT(shop) AS itemsCount
        FROM ShopEntity
        LEFT JOIN (SELECT shop FROM ItemEntity WHERE completed = 0) ON shop == ShopEntity.id
        GROUP BY ShopEntity.id
        """
    )
    fun getAllWithUncompletedItemsCount(): Observable<List<ShopWithItemsCount>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(entity: ShopEntity): Id
}
