package net.chmielowski.shoppinglist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopId(val value: Int) : Parcelable

data class ItemId(val value: Int)

data class IconId(val value: Int)

typealias Name = String

typealias Quantity = String
