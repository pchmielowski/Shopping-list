package net.chmielowski.shoppinglist.data.item

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import net.chmielowski.shoppinglist.Id

@Entity(indices = [Index("name", "shop", unique = true)])
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Id? = null,
    val name: String,
    val completed: Boolean = false,
    val quantity: String,
    val shop: Id,
    val deleted: Boolean = false
)
