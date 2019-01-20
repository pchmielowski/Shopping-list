package net.chmielowski.shoppinglist.shop

import androidx.annotation.VisibleForTesting
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
) {

    companion object {

        @VisibleForTesting(otherwise = VisibleForTesting.NONE)
        fun dummy(shopId: Int, name: String) =
            ShopWithItemsCount(ShopId(shopId), name, null, IconId(0), 0)
    }
}
