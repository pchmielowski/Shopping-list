package net.chmielowski.shoppinglist.view.shops

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import dagger.Lazy
import io.reactivex.Observable
import net.chmielowski.shoppinglist.ObserveData
import net.chmielowski.shoppinglist.ObserveShopsType
import net.chmielowski.shoppinglist.Shop
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.addshop.IconViewModel
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import javax.inject.Inject

@SuppressLint("CheckResult")
class ShopListViewModel(observeShops: ObserveShopsType) : ViewModel() {
    class Factory @Inject constructor(observeShops: Lazy<ObserveShopsType>) :
        BaseViewModelFactory<ShopListViewModel>({ ShopListViewModel(observeShops.get()) })

    val shops = NonNullMutableLiveData<List<ShopViewModel>>(emptyList())

    init {
        observeShops()
            .map(Iterable<Shop>::toViewModels)
            .subscribe(shops::postValue)
    }
}

operator fun <T> ObserveData<Unit, T>.invoke() = invoke(Unit)

private fun Iterable<Shop>.toViewModels() = map(Shop::toViewModel)

private fun Shop.toViewModel(): ShopViewModel {
    return ShopViewModel(id, name, color, IconViewModel.drawable(icon.id))
}
