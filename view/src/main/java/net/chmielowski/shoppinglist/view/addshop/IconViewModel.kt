package net.chmielowski.shoppinglist.view.addshop

import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.IconMapper.drawableFromId

data class IconViewModel(override val id: Id, val res: Int, val isSelected: Boolean = false) : HasId {
    companion object {
        fun fromId(id: Id) = IconViewModel(id, drawableFromId(id))

        fun fromId(id: Id, selectedIcon: Id) = IconViewModel(id, drawableFromId(id), isSelected = selectedIcon == id)
    }
}
