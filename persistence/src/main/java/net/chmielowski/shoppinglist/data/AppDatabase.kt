package net.chmielowski.shoppinglist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import net.chmielowski.shoppinglist.IconId
import net.chmielowski.shoppinglist.ItemId
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.data.item.ItemDao
import net.chmielowski.shoppinglist.data.item.ItemEntity
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.data.shop.ShopEntity

@Database(entities = [ItemEntity::class, ShopEntity::class], version = 6)
@TypeConverters(BooleanConverter::class, IdConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val itemDao: ItemDao

    abstract val shopDao: ShopDao
}

class IdConverters {

    @TypeConverter
    fun fromId(id: ShopId) = id.value

    @TypeConverter
    fun toShopId(value: Int) = ShopId(value)

    @TypeConverter
    fun fromId(id: ItemId) = id.value

    @TypeConverter
    fun toItemId(value: Int) = ItemId(value)

    @TypeConverter
    fun fromId(id: IconId) = id.value

    @TypeConverter
    fun toIconId(value: Int) = IconId(value)
}
