package net.chmielowski.shoppinglist.view.shops

import androidx.annotation.VisibleForTesting
import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.Id

data class ShopViewModel(
    override val id: Id,
    val itemsCount: String,
    val appearance: Appearance
) : HasId {
    data class Appearance(
        val name: String,
        val icon: Int,
        val colorVisible: Boolean,
        val color: Int?
    )

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.NONE)
        fun dummy(id: Id, name: String) = ShopViewModel(
            id, "", Appearance(name, 0, false, null)
        )
    }
}