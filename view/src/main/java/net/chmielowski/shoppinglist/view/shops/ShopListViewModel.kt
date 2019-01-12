package net.chmielowski.shoppinglist.view.shops

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import net.chmielowski.shoppinglist.ObserveShopsType
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData

@SuppressLint("CheckResult")
class ShopListViewModel(observeShops: ObserveShopsType, mapper: ShopViewModelMapper) : ViewModel() {

    val shops = NonNullMutableLiveData<List<ShopViewModel>>(emptyList())
    val noShops = NonNullMutableLiveData<Boolean>(false)

    init {
        observeShops(Unit)
            .map(mapper::toViewModels)
            .subscribe {
                noShops.postValue(it.isEmpty())
                shops.postValue(it)
            }
    }
}
