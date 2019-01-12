package net.chmielowski.shoppinglist.view.addshop

import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.IconId

data class IconViewModel(
    override val id: IconId,
    val res: Int,
    val isSelected: Boolean = false
) : HasId<IconId>
