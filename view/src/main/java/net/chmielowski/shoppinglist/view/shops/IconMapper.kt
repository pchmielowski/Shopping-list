package net.chmielowski.shoppinglist.view.shops

import androidx.annotation.DrawableRes
import net.chmielowski.shoppinglist.Id

interface IconMapper {
    @DrawableRes
    fun toDrawableRes(id: Id): Int

    object Fake : IconMapper {
        override fun toDrawableRes(id: Id) = 0
    }
}