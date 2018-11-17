package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Lazy
import net.chmielowski.shoppinglist.*
import net.chmielowski.shoppinglist.shop.ReadShopNameParams
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import javax.inject.Inject

@SuppressLint("CheckResult")
class ItemsViewModel(
    readShopName: ReadShopNameType,
    readItems: ObserveItemsType,
    private val setCompleted: CompletableAction<SetCompletedParams>,
    shopId: Id
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

    init {
        readShopName(ReadShopNameParams(shopId))
            .subscribe(shopName::postValue)
        readItems(NonCompleted(shopId))
            .map(this::toViewModels)
            .subscribe(items::postValue)
    }

    private fun <T : HasId> NonNullMutableLiveData<out Iterable<T>>.findWithId(id: Id) =
        value.single { it.id == id }

    fun onToggled(id: Id, completed: Boolean) {
        setCompleted(SetCompletedParams(id, completed))
            .subscribe()
    }

    private fun NonNullMutableLiveData<out Iterable<ItemViewModel>>.update(
        updatedItem: ItemViewModel,
        completed: Boolean
    ) =
        value.map {
            when (it) {
                updatedItem -> updatedItem.copy(completed = completed)
                else -> it
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
