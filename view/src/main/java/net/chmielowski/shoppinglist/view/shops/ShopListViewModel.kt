package net.chmielowski.shoppinglist.view.shops

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import dagger.Lazy
import net.chmielowski.shoppinglist.ObserveData
import net.chmielowski.shoppinglist.ObserveShopsType
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import javax.inject.Inject

@SuppressLint("CheckResult")
class ShopListViewModel(observeShops: ObserveShopsType, mapper: ShopViewModelMapper) : ViewModel() {
    class Factory @Inject constructor(
        observeShops: Lazy<ObserveShopsType>,
        mapper: Lazy<ShopViewModelMapper>
    ) : BaseViewModelFactory<ShopListViewModel>({ ShopListViewModel(observeShops.get(), mapper.get()) })

    val shops = NonNullMutableLiveData<List<ShopViewModel2>>(emptyList())
    val noShops = NonNullMutableLiveData<Boolean>(false)

    init {
        observeShops()
            .map(mapper::toViewModels)
            .subscribe {
                noShops.postValue(it.isEmpty())
                shops.postValue(it)
            }
    }
}

operator fun <T> ObserveData<Unit, T>.invoke() = invoke(Unit)
