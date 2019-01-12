package net.chmielowski.shoppinglist.item

import net.chmielowski.shoppinglist.ShopId

sealed class ReadItemsParams(val shopId: ShopId)

class NonCompletedOnly(shopId: ShopId) : ReadItemsParams(shopId)
class All(shopId: ShopId) : ReadItemsParams(shopId)
