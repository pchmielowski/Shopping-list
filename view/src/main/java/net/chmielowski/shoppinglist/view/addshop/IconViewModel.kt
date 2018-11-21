package net.chmielowski.shoppinglist.view.addshop

import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.Id

data class IconViewModel(
    override val id: Id,
    val res: Int,
    val isSelected: Boolean = false
) : HasId
