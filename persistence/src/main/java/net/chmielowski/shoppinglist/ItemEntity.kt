package net.chmielowski.shoppinglist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Id? = null,
    val name: String,
    val completed: Boolean = false,
    val quantity: Int? = null,
    val shop: Id
)
