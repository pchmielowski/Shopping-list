package net.chmielowski.shoppinglist.data.shop

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.shop.ShopEntity
import net.chmielowski.shoppinglist.shop.ShopWithItemNumber

@Dao
interface ShopDao {
    @Query("SELECT * FROM ShopEntity")
    fun getAll(): Flowable<List<ShopEntity>>

    @Query(
        """
        SELECT ShopEntity.id, ShopEntity.name, COUNT(*) AS itemsNumber
        FROM ShopEntity
        INNER JOIN ItemEntity ON ItemEntity.shop == ShopEntity.id
        GROUP BY ShopEntity.id
        """
    )
    fun getAllAlt(): Flowable<List<ShopWithItemNumber>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(entity: ShopEntity): Id
}