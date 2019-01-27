package net.chmielowski.shoppinglist.shop

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
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
    suspend fun findShopById(id: ShopId): ShopEntity

    @Query("SELECT COUNT (*) FROM ShopEntity WHERE name = :name")
    suspend fun countShopByName(name: Name): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: ShopEntity): Long

    @Query("DELETE FROM ShopEntity WHERE id = :shop")
    suspend fun delete(shop: ShopId)

}
