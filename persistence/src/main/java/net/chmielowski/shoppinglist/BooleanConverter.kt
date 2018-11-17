package net.chmielowski.shoppinglist

import androidx.room.TypeConverter

class BooleanConverter {
    @TypeConverter
    fun toBoolean(int: Int) = when (int) {
        0 -> false
        1 -> true
        else -> throw IllegalArgumentException("Can not convert $int to boolean!")
    }

    @TypeConverter
    fun toInt(boolean: Boolean) = when (boolean) {
        false -> 0
        true -> 1
    }
}
