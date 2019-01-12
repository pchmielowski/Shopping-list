package net.chmielowski.shoppinglist.view.shops

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import net.chmielowski.shoppinglist.shop.ShopRepository
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData

@SuppressLint("CheckResult")
class ShopListViewModel(shopRepository: ShopRepository, mapper: ShopViewModelMapper) : ViewModel() {

    val shops = NonNullMutableLiveData<List<ShopViewModel>>(emptyList())
    val noShops = NonNullMutableLiveData<Boolean>(false)

    init {
        shopRepository.observe()
            .map(mapper::toViewModels)
            .subscribe {
                noShops.postValue(it.isEmpty())
                shops.postValue(it)
            }
    }
}
