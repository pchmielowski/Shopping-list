package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import dagger.Lazy
import net.chmielowski.shoppinglist.*
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import javax.inject.Inject

@SuppressLint("CheckResult")
class AddItemViewModel(
    private val addItem: ActionWithResult<AddItemParams, Item>,
    private val readItems: ActionWithResult<ReadItemsParams, List<Item>>,
    private val setCompleted: CompletableAction<SetCompletedParams>
) : ViewModel() {

    class Factory @Inject constructor(
        addItem: Lazy<ActionWithResult<AddItemParams, Item>>,
        readItems: Lazy<ActionWithResult<ReadItemsParams, List<@JvmSuppressWildcards Item>>>,
        setCompleted: Lazy<CompletableAction<SetCompletedParams>>
    ) : BaseViewModelFactory<AddItemViewModel>({ AddItemViewModel(addItem.get(), readItems.get(), setCompleted.get()) })

    val suggestions = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())

    private var _newItemName: String? = null
    private val newItem: String
        get() = _newItemName ?: throw IllegalStateException("User has not entered a new item name.")

    private var quantity: String? = null

    fun onNewItemNameChange(name: String) {
        _newItemName = name
        displaySuggestions()
    }

    private fun displaySuggestions() {
        readItems(Completed)
            .map(this::toViewModels)
            .subscribe(suggestions::postValue)
    }

    fun onQuantityChange(qntty: String) {
        quantity = qntty
    }

    fun onAddingConfirmed() {
        suggestions.value = emptyList()
        addItem(AddItemParams(newItem, quantity))
            .subscribe()
        _newItemName = null
        quantity = null
    }

    fun onSuggestionChosen(item: Id) {
        _newItemName = null
        suggestions.value = emptyList()

        setCompleted(SetCompletedParams(item, false))
            .subscribe()
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
