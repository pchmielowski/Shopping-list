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

interface GetShopAppearanceType : BlockingActionWithResult<Id, ShopAppearance>

interface AddShopType : BlockingActionWithResult<AddShopParams, AddShopResult>


interface ObserveItemsType : ObserveData<ReadItemsParams, List<@JvmSuppressWildcards Item>>

interface AddItemType : BlockingAction<AddItemParams>

interface SetCompletedType : BlockingAction<SetCompletedParams>

typealias DeleteType = BlockingAction<Id>

typealias UnDeleteType = BlockingAction<Id>
