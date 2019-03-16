package net.chmielowski.shoppinglist.view.shops

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import net.chmielowski.shoppinglist.shop.ShopRepository
import net.chmielowski.utils.lifedata.NonNullMutableLiveData

class ShopListViewModel(shopRepository: ShopRepository, mapper: ShopViewModelMapper) : ViewModel() {

    val shops = NonNullMutableLiveData<List<ShopViewModel>>(emptyList())
    val noShops = NonNullMutableLiveData(false)

    private val observingShops: Disposable

    init {
        observingShops = shopRepository.observe()
            .map(mapper::toViewModels)
            .subscribe { list ->
                noShops.postValue(list.isEmpty())
                shops.postValue(list)
            }
    }

    override fun onCleared() {
        observingShops.dispose()
    }
}
