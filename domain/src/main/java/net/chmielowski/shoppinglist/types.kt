package net.chmielowski.shoppinglist

import net.chmielowski.shoppinglist.shop.AddShopParams
import net.chmielowski.shoppinglist.shop.AddShopResult
import net.chmielowski.shoppinglist.shop.ShopAppearance
import net.chmielowski.shoppinglist.view.items.AddItemParams

typealias Id = Long

typealias ObserveShopsType = ObserveData<Unit, List<@JvmSuppressWildcards Shop>>

typealias GerShopAppearance = ActionWithResult<Id, ShopAppearance>

typealias AddShopType = ActionWithResult<AddShopParams, AddShopResult>


typealias ObserveItemsType = ObserveData<ReadItemsParams, List<@JvmSuppressWildcards Item>>

typealias AddItemType = CompletableAction<AddItemParams>

typealias SetCompletedType = CompletableAction<SetCompletedParams>

typealias DeleteType = CompletableAction<Id>
