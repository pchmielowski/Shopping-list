package net.chmielowski.shoppinglist

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("name", "shop", unique = true)])
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Id? = null,
    val name: String,
    val completed: Boolean = false,
    val quantity: String,
    val shop: Id
)
