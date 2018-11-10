package net.chmielowski.shoppinglist

sealed class ReadItemsParams(val showCompleted: Boolean) : DataAction.Params

object NonCompleted : ReadItemsParams(false)
object Completed : ReadItemsParams(false)