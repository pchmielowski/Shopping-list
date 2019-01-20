package net.chmielowski.shoppinglist.view.shops

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import net.chmielowski.shoppinglist.shop.ShopRepository
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData

class ShopListViewModel(shopRepository: ShopRepository, mapper: ShopViewModelMapper) : ViewModel() {

    val shops = NonNullMutableLiveData<List<ShopViewModel>>(emptyList())
    val noShops = NonNullMutableLiveData<Boolean>(false)

    private val disposable: Disposable

    init {
        disposable = shopRepository.observe()
            .map(mapper::toViewModels)
            .subscribe {
                noShops.postValue(it.isEmpty())
                shops.postValue(it)
            }
    }

    override fun onCleared() {
        disposable.dispose()
    }
}
