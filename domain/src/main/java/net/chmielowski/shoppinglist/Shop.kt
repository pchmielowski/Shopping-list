package net.chmielowski.shoppinglist

typealias ShopColor = Pair<Int, Int>

data class ShopIcon(val id: Id)

data class Shop(
    val id: Id,
    val name: String,
    val color: ShopColor?,
    val icon: ShopIcon,
    val itemsCount: Int
)
