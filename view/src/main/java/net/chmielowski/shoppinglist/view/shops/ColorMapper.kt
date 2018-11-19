package net.chmielowski.shoppinglist.view.shops

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import net.chmielowski.shoppinglist.shop.ShopColor

interface ColorMapper {
    @ColorInt
    fun toInt(color: ShopColor): Int

    object Fake : ColorMapper {
        override fun toInt(color: ShopColor) = 0
    }
}