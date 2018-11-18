package net.chmielowski.shoppinglist.item

import net.chmielowski.shoppinglist.Id

sealed class ReadItemsParams(val shopId: Id)

class NonCompletedOnly(shopId: Id) : ReadItemsParams(shopId)
class All(shopId: Id) : ReadItemsParams(shopId)
