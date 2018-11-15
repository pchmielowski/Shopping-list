package net.chmielowski.shoppinglist.shop

import io.reactivex.Observable
import net.chmielowski.shoppinglist.ObserveShopsType
import net.chmielowski.shoppinglist.Shop
import javax.inject.Inject

internal class ObserveShops @Inject constructor() : ObserveShopsType {
    override fun invoke(params: Unit) = Observable.never<List<Shop>>()!!
}
