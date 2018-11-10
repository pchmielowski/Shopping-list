package net.chmielowski.shoppinglist

sealed class ReadItemsParams(val showCompleted: Boolean) : ReadAction.Params

object NonCompleted : ReadItemsParams(false)
object Completed : ReadItemsParams(false)