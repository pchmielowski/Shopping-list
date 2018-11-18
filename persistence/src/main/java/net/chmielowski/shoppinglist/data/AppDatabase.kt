package net.chmielowski.shoppinglist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.chmielowski.shoppinglist.data.item.ItemDao
import net.chmielowski.shoppinglist.data.item.ItemEntity
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.data.shop.ShopEntity

@Database(entities = [ItemEntity::class, ShopEntity::class], version = 6)
@TypeConverters(BooleanConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val itemDao: ItemDao

    abstract val shopDao: ShopDao
}
