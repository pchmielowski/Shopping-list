package net.chmielowski.shoppinglist.data.shop

import androidx.room.Embedded
import net.chmielowski.shoppinglist.IconId
import net.chmielowski.shoppinglist.ShopId

data class ShopWithItemsCount(
    val id: ShopId,
    val name: String,
    @Embedded
    val color: ColorEntity?,
    val icon: IconId,
    val itemsCount: Int
)
