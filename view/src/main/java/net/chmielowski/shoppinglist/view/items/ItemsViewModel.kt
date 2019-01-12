package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.chmielowski.shoppinglist.GetShopAppearanceType
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ObserveItemsType
import net.chmielowski.shoppinglist.SetCompletedType
import net.chmielowski.shoppinglist.item.All
import net.chmielowski.shoppinglist.item.Item
import net.chmielowski.shoppinglist.item.NonCompletedOnly
import net.chmielowski.shoppinglist.item.SetCompletedParams
import net.chmielowski.shoppinglist.view.HasDispatcher
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import net.chmielowski.shoppinglist.view.shops.ShopViewModel
import net.chmielowski.shoppinglist.view.shops.ShopViewModelMapper

@SuppressLint("CheckResult")
class ItemsViewModel(
    getShopAppearance: GetShopAppearanceType,
    private val observeItems: ObserveItemsType,
    private val setCompleted: SetCompletedType,
    mapper: ShopViewModelMapper,
    override val dispatcher: CoroutineDispatcher,
    private val shopId: Id
) : ViewModel(), HasDispatcher {

    val shop = MutableLiveData<ShopViewModel.Appearance>()
    val items = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())

    private var observingItems: Disposable

    init {
        launch {
            shop.postValue(mapper.toAppearance(getShopAppearance(shopId)))
        }
        observingItems = observeItems(NonCompletedOnly(shopId))
            .map(this::toViewModels)
            .subscribe(items::postValue)
    }

    fun onToggleShowCompleted(showCompleted: Boolean) {
        observingItems.dispose()
        observingItems = observeItems(
            if (showCompleted) All(shopId) else NonCompletedOnly(
                shopId
            )
        )
            .map(this::toViewModels)
            .subscribe(items::postValue)
    }

    fun onToggled(id: Id, completed: Boolean) {
        GlobalScope.launch(dispatcher) {
            setCompleted(SetCompletedParams(id, completed))
        }
    }

    private fun toViewModels(domainModels: Iterable<Item>) = domainModels.map(this::toViewModel)

    private fun toViewModel(domainModel: Item) = ItemViewModel(
        domainModel.id,
        domainModel.name,
        domainModel.completed,
        domainModel.quantity
    )
}
