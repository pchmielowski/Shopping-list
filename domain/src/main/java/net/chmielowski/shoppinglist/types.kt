package net.chmielowski.shoppinglist

import net.chmielowski.shoppinglist.item.AddItemParams
import net.chmielowski.shoppinglist.item.Item
import net.chmielowski.shoppinglist.item.ReadItemsParams
import net.chmielowski.shoppinglist.item.SetCompletedParams
import net.chmielowski.shoppinglist.shop.AddShopParams
import net.chmielowski.shoppinglist.shop.AddShopResult
import net.chmielowski.shoppinglist.shop.Shop
import net.chmielowski.shoppinglist.shop.ShopAppearance

typealias Id = Long

typealias ObserveShopsType = ObserveData<Unit, List<@JvmSuppressWildcards Shop>>

typealias GetShopAppearanceType = BlockingActionWithResult<Id, ShopAppearance>

typealias AddShopType = BlockingActionWithResult<AddShopParams, AddShopResult>


typealias ObserveItemsType = ObserveData<ReadItemsParams, List<@JvmSuppressWildcards Item>>

typealias AddItemType = CompletableAction<AddItemParams>

typealias SetCompletedType = CompletableAction<SetCompletedParams>

typealias DeleteType = CompletableAction<Id>
