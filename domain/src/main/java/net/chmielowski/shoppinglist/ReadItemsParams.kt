package net.chmielowski.shoppinglist

sealed class ReadItemsParams(val showCompleted: Boolean)

object NonCompleted : ReadItemsParams(false)
object Completed : ReadItemsParams(false)
