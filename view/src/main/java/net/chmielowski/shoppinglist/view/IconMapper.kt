package net.chmielowski.shoppinglist.view

import net.chmielowski.shoppinglist.Id


@Deprecated("")
object IconMapper {
    fun drawableFromId(id: Id) = drawables[id]!!

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