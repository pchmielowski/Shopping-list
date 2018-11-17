package net.chmielowski.shoppinglist

sealed class ReadItemsParams(val shopId: Id)

class NonCompletedOnly(shopId: Id) : ReadItemsParams(shopId)
class All(shopId: Id) : ReadItemsParams(shopId)
