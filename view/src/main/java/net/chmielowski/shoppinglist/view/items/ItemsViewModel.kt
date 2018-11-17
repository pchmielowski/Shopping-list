package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Lazy
import io.reactivex.disposables.Disposable
import net.chmielowski.shoppinglist.*
import net.chmielowski.shoppinglist.shop.ReadShopNameParams
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import javax.inject.Inject

@SuppressLint("CheckResult")
class ItemsViewModel(
    readShopName: ReadShopNameType,
    private val observeItems: ObserveItemsType,
    private val setCompleted: CompletableAction<SetCompletedParams>,
    private val shopId: Id
) : ViewModel() {

    class Factory(
        readShopName: Lazy<ReadShopNameType>,
        observeItems: Lazy<ObserveItemsType>,
        setCompleted: Lazy<SetCompletedType>,
        shopId: Id
    ) :
        BaseViewModelFactory<ItemsViewModel>({
            ItemsViewModel(
                readShopName.get(),
                observeItems.get(),
                setCompleted.get(),
                shopId
            )
        }) {
        class Builder @Inject constructor(
            private val readShopName: Lazy<ReadShopNameType>,
            private val observeItems: Lazy<ObserveItemsType>,
            private val setCompleted: Lazy<SetCompletedType>
        ) {
            fun build(shopId: Id) = Factory(readShopName, observeItems, setCompleted, shopId)
        }
    }

    val shopName = MutableLiveData<String>()
    val items = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())

    var observingItems: Disposable

    init {
        readShopName(ReadShopNameParams(shopId))
            .subscribe(shopName::postValue)
        observingItems = observeItems(NonCompletedOnly(shopId))
            .map(this::toViewModels)
            .subscribe(items::postValue)
    }

    fun onToggleShowCompleted(showCompleted: Boolean) {
        observingItems.dispose()
        observingItems = observeItems(if (showCompleted) All(shopId) else NonCompletedOnly(shopId))
            .map(this::toViewModels)
            .subscribe(items::postValue)
    }

    fun onToggled(id: Id, completed: Boolean) {
        setCompleted(SetCompletedParams(id, completed))
            .subscribe()
    }

    private fun toViewModels(domainModels: Iterable<Item>) = domainModels.map(this::toViewModel)

    private fun toViewModel(domainModel: Item) = ItemViewModel(
        domainModel.id,
        domainModel.name,
        domainModel.completed,
        domainModel.quantity
    )
}
