package net.chmielowski.shoppinglist.view.addshop

import net.chmielowski.shoppinglist.HasId
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.R

data class IconViewModel(override val id: Id, val res: Int, val isSelected: Boolean = false) : HasId {
    companion object {
        fun fromId(id: Id) = IconViewModel(id, drawables[id]!!)

        private val drawables by lazy {
            arrayOf(
                R.drawable.ic_shop_electronic,
                R.drawable.ic_shop_grocery,
                R.drawable.ic_shop_pharmacy,
                R.drawable.ic_shop_sport,
                R.drawable.ic_shop_stationers,
                R.drawable.ic_shop_children,
                R.drawable.ic_shop_business,
                R.drawable.ic_shop_rtv
            ).withIndex().associate { it.index.toLong() to it.value }
        }

    }
}
