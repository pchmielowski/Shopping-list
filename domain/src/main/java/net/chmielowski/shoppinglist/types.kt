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

interface ObserveShopsType : ObserveData<Unit, List<@JvmSuppressWildcards Shop>>

typealias GetShopAppearanceType = BlockingActionWithResult<Id, ShopAppearance>

typealias AddShopType = BlockingActionWithResult<AddShopParams, AddShopResult>


typealias ObserveItemsType = ObserveData<ReadItemsParams, List<@JvmSuppressWildcards Item>>

typealias AddItemType = BlockingAction<AddItemParams>

typealias SetCompletedType = BlockingAction<SetCompletedParams>

typealias DeleteType = BlockingAction<Id>
