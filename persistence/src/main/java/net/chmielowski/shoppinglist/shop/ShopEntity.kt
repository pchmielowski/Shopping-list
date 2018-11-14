package net.chmielowski.shoppinglist.shop

import androidx.room.Entity
import androidx.room.PrimaryKey
import net.chmielowski.shoppinglist.Id

@Entity
data class ShopEntity(
    val name: String,
    val color: Float,
    val icon: Id?
) {
    @PrimaryKey(autoGenerate = true)
    val id: Id? = null
}
