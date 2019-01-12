package net.chmielowski.shoppinglist.view.shops

import androidx.annotation.DrawableRes
import net.chmielowski.shoppinglist.IconId

interface IconMapper {

    @DrawableRes
    fun toDrawableRes(id: IconId): Int

    object Fake : IconMapper {
        override fun toDrawableRes(id: IconId) = 0
    }
}