package net.chmielowski.shoppinglist.item

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import net.chmielowski.shoppinglist.ItemId
import net.chmielowski.shoppinglist.ShopId

// TODO: foreign key with shop
@Entity(indices = [Index("name", "shop", unique = true)])
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: ItemId? = null,
    val name: String,
    val completed: Boolean = false,
    val quantity: String,
    val shop: ShopId,
    val deleted: Boolean = false
)
