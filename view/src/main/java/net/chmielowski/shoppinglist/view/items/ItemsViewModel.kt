package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineDispatcher
import net.chmielowski.shoppinglist.ItemId
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.item.Item
import net.chmielowski.shoppinglist.item.ItemRepository
import net.chmielowski.shoppinglist.item.ReadItemsParams.All
import net.chmielowski.shoppinglist.item.ReadItemsParams.NonCompletedOnly
import net.chmielowski.shoppinglist.shop.ShopRepository
import net.chmielowski.shoppinglist.view.HasDispatcher
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import net.chmielowski.shoppinglist.view.shops.ShopViewModel
import net.chmielowski.shoppinglist.view.shops.ShopViewModelMapper

@SuppressLint("CheckResult")
class ItemsViewModel(
    private val shopRepository: ShopRepository,
    private val itemRepository: ItemRepository,
    mapper: ShopViewModelMapper,
    override val dispatcher: CoroutineDispatcher,
    private val shopId: ShopId
) : ViewModel(), HasDispatcher {

    val shop = MutableLiveData<ShopViewModel.Appearance>()
    val items = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())

    private var observingItems: Disposable

    init {
        launch {
            shop.postValue(mapper.toAppearance(shopRepository.getAppearance(shopId)))
        }
        observingItems = itemRepository.observe(NonCompletedOnly, shopId)
            .map(this::toViewModels)
            .subscribe(items::postValue)
    }

    fun onToggleShowCompleted(showCompleted: Boolean) {
        observingItems.dispose()
        observingItems = itemRepository.observe(
            if (showCompleted) All
            else NonCompletedOnly,
            shopId
        ).map(this::toViewModels)
            .subscribe(items::postValue)
    }

    fun onToggled(id: ItemId, completed: Boolean) = launch {
        itemRepository.setCompleted(id, completed)
    }

    private fun toViewModels(domainModels: Iterable<Item>) =
        domainModels.map(this::toViewModel)

    private fun toViewModel(domainModel: Item) = domainModel.run {
        ItemViewModel(id, name, isCompleted, quantity)
    }
}
