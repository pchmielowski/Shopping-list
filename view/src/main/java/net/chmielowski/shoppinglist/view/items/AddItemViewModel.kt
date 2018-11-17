package net.chmielowski.shoppinglist.view.items

import androidx.lifecycle.ViewModel
import dagger.Lazy
import net.chmielowski.shoppinglist.*
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import javax.inject.Inject

class AddItemViewModel(
    private val addItem: ActionWithResult<AddItemParams, Item>
) : ViewModel() {

    class Factory @Inject constructor(
        addItem: Lazy<ActionWithResult<AddItemParams, Item>>
    ) : BaseViewModelFactory<AddItemViewModel>({ AddItemViewModel(addItem.get()) })

    val suggestions = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())

    private var _newItemName: String? = null
    private val newItem: String
        get() = _newItemName ?: throw IllegalStateException("User has not entered a new item name.")

    private var quantity: String? = null

    fun onNewItemNameChange(name: String) {
        _newItemName = name
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
}
