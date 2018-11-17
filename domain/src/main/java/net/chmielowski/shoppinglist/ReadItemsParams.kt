package net.chmielowski.shoppinglist

sealed class ReadItemsParams(val showCompleted: Boolean, val shopId: Id)

class NonCompleted(shopId: Id) : ReadItemsParams(false, shopId)
class Completed(shopId: Id) : ReadItemsParams(false, shopId)
