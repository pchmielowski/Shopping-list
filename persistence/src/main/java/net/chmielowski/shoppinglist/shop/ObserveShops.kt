package net.chmielowski.shoppinglist.shop

import io.reactivex.Observable
import net.chmielowski.shoppinglist.ObserveData
import net.chmielowski.shoppinglist.Shop
import javax.inject.Inject

class ObserveShops @Inject constructor() : ObserveData<Unit, List<@JvmSuppressWildcards Shop>> {
    override fun invoke(params: Unit) = Observable.never<List<Shop>>()!!
}
