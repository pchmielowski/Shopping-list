package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Lazy
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.IconMapper.drawableFromId
import net.chmielowski.shoppinglist.view.ShopViewModel
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import javax.inject.Inject

@SuppressLint("CheckResult")
class ItemsViewModel(
    getShopAppearance: GetShopAppearanceType,
    private val observeItems: ObserveItemsType,
    private val setCompleted: SetCompletedType,
    private val shopId: Id,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    class Factory(
        gerShop: Lazy<GetShopAppearanceType>,
        observeItems: Lazy<ObserveItemsType>,
        setCompleted: Lazy<SetCompletedType>,
        shopId: Id
    ) :
        BaseViewModelFactory<ItemsViewModel>({
            ItemsViewModel(
                gerShop.get(),
                observeItems.get(),
                setCompleted.get(),
                shopId
            )
        }) {
        class Builder @Inject constructor(
            private val gerShop: Lazy<GetShopAppearanceType>,
            private val observeItems: Lazy<ObserveItemsType>,
            private val setCompleted: Lazy<SetCompletedType>
        ) {
            fun build(shopId: Id) = Factory(gerShop, observeItems, setCompleted, shopId)
        }
    }

    val shop = MutableLiveData<ShopViewModel.Appearance>()
    val items = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())

    private var observingItems: Disposable

    init {
        GlobalScope.launch(dispatcher) {
            val shopAppearance = getShopAppearance(shopId)
            // TODO: use mapper
            val model = ShopViewModel.Appearance(
                shopAppearance.name,
                shopAppearance.color,
                drawableFromId(shopAppearance.icon.id)
            )
            shop.postValue(model)
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
