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

@Deprecated("Use repository")
interface ObserveShopsType : ObserveData<Unit, List<@JvmSuppressWildcards Shop>>

@Deprecated("Use repository")
interface GetShopAppearanceType : BlockingActionWithResult<Id, ShopAppearance>

@Deprecated("Use repository")
interface AddShopType : BlockingActionWithResult<AddShopParams, AddShopResult>

data class ShopId(val value: Int)

data class ItemId(val value: Int)

data class IconId(val value: Int)

typealias Name = String

typealias Quantity = String

@Deprecated("Use repository")
interface ObserveItemsType : ObserveData<ReadItemsParams, List<@JvmSuppressWildcards Item>>

@Deprecated("Use repository")
interface AddItemType : BlockingAction<AddItemParams>

@Deprecated("Use repository")
interface SetCompletedType : BlockingAction<SetCompletedParams>

@Deprecated("Use repository")
typealias DeleteType = BlockingAction<Id>
