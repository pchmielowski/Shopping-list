package net.chmielowski.shoppinglist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Id? = null,
    val name: String
)