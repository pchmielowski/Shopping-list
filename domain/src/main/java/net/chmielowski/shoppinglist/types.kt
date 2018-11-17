package net.chmielowski.shoppinglist

import net.chmielowski.shoppinglist.shop.ReadShopNameParams
import net.chmielowski.shoppinglist.view.items.AddItemParams

typealias Id = Long

typealias ObserveShopsType = ObserveData<Unit, List<@JvmSuppressWildcards Shop>>

typealias ObserveItemsType = ObserveData<ReadItemsParams, List<@JvmSuppressWildcards Item>>

typealias ReadShopNameType = ActionWithResult<ReadShopNameParams, String>

typealias AddItemType = CompletableAction<AddItemParams>

typealias SetCompletedType = CompletableAction<SetCompletedParams>
