package net.chmielowski.shoppinglist.shop

import androidx.room.Embedded
import net.chmielowski.shoppinglist.Id

data class ShopWithItemsCount(
    val id: Id,
    val name: String,
    @Embedded
    val color: ColorEntity?,
    val icon: Id,
    val itemsCount: Int
)