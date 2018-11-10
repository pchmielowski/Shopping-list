package net.chmielowski.shoppinglist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity(
    @PrimaryKey val id: Id,
    val name: String
)