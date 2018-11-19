package net.chmielowski.shoppinglist.view.shops

import androidx.annotation.DrawableRes
import net.chmielowski.shoppinglist.Id

interface IconMapper2 {
    @DrawableRes
    fun toDrawableRes(id: Id): Int

    object Fake : IconMapper2 {
        override fun toDrawableRes(id: Id) = 0
    }
}