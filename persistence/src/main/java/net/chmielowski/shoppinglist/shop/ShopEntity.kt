package net.chmielowski.shoppinglist.shop

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.chmielowski.shoppinglist.Id

@Entity
data class ShopEntity(
    val name: String,
    @Embedded
    val color: ColorEntity?,
    val icon: Id
) {
    @PrimaryKey(autoGenerate = true)
    var id: Id? = null
}

data class ShopWithItemsCount(
    val id: Id,
    val name: String,
    @Embedded
    val color: ColorEntity?,
    val icon: Id,
    val itemsCount: Int
) {
    override fun toString() = "#$id $name: $itemsCount"
}
