package net.chmielowski.shoppinglist

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.chmielowski.shoppinglist.data.item.ItemDao
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.shop.ShopEntity

@Database(entities = [ItemEntity::class, ShopEntity::class], version = 1)
@TypeConverters(BooleanConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val itemDao: ItemDao

    abstract val shopDao: ShopDao
}
