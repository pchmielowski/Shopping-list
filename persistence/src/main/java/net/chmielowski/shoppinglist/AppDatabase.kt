package net.chmielowski.shoppinglist

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ItemEntity::class], version = 1)
@TypeConverters(BooleanConverter::class)
abstract class AppDatabase : RoomDatabase() {
    // TODO: property
    abstract fun dao(): ItemDao
}
