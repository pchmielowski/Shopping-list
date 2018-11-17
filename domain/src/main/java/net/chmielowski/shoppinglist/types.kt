package net.chmielowski.shoppinglist

import net.chmielowski.shoppinglist.view.items.AddItemParams

typealias Id = Long

typealias ObserveShopsType = ObserveData<Unit, List<@JvmSuppressWildcards Shop>>

typealias ObserveItemsType = ObserveData<ReadItemsParams, List<@JvmSuppressWildcards Item>>

typealias AddItemType = ActionWithResult<AddItemParams, Item>

typealias SetCompletedType = CompletableAction<SetCompletedParams>
