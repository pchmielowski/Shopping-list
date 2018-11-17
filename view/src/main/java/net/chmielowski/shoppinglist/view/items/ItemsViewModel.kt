package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import dagger.Lazy
import net.chmielowski.shoppinglist.*
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import javax.inject.Inject

@SuppressLint("CheckResult")
class ItemsViewModel(
    readItems: ObserveItemsType,
    private val setCompleted: CompletableAction<SetCompletedParams>,
    shopId: Id
) : ViewModel() {

    class Factory(observeItems: Lazy<ObserveItemsType>, setCompleted: Lazy<SetCompletedType>, shopId: Id) :
        BaseViewModelFactory<ItemsViewModel>({ ItemsViewModel(observeItems.get(), setCompleted.get(), shopId) }) {
        class Builder @Inject constructor(
            private val observeItems: Lazy<ObserveItemsType>,
            private val setCompleted: Lazy<SetCompletedType>
        ) {
            fun build(shopId: Id) = Factory(observeItems, setCompleted, shopId)
        }
    }

    val items = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())

    init {
        readItems(NonCompleted(shopId))
            .map(this::toViewModels)
            .subscribe(items::postValue)
    }

    private fun <T : HasId> NonNullMutableLiveData<out Iterable<T>>.findWithId(id: Id) =
        value.single { it.id == id }

    fun onToggled(id: Id) {
        val updatedItem = items.findWithId(id)
        val completed = !updatedItem.completed
        items.value = items.update(updatedItem, completed)
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
        formatQuantity(domainModel.quantity)
    )

    private fun formatQuantity(quantity: Item.Quantity?) = when (quantity) {
        null -> null
        is Item.Quantity.NoUnit -> quantity.value.toString()
        is Item.Quantity.Weight -> TODO()
    }
}
