package net.chmielowski.shoppinglist.data.shop

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.shop.ShopEntity

@Dao
interface ShopDao {
    @Query("SELECT * FROM ShopEntity")
    fun getAll(): Observable<List<ShopEntity>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(entity: ShopEntity): Id
}