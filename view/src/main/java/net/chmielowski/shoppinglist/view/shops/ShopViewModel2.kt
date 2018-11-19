package net.chmielowski.shoppinglist.view.shops

import androidx.annotation.VisibleForTesting
import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.Id

data class ShopViewModel2(
    override val id: Id,
    val name: String,
    val icon: Int,
    val itemsCount: String,
    val colorVisible: Boolean,
    val color: Int?
) : HasId {
    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.NONE)
        fun dummy(id: Id, name: String) = ShopViewModel2(
            id, name, 0, "", false, null
        )
    }
}