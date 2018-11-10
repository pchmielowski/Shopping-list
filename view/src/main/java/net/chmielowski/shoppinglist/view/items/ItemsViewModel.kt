package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import net.chmielowski.shoppinglist.*

class ItemsViewModel(
    private val addItem: WriteAction<String>,
    private val readItems: ReadAction<ReadItemsParams, List<Item>>
) : ViewModel() {

    val entering = NonNullMutableLiveData<Boolean>(false)
    val items = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())
    val suggestions = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())

    private var _newItem: String? = null
    private val newItem: String
        get() = _newItem ?: throw IllegalStateException("User has not entered a new item name.")

    fun onAddNew() {
        entering.value = true
    }

    @SuppressLint("CheckResult")
    fun onTextChange(name: String) {
        _newItem = name
        readItems(Completed)
            .map(this::toViewModels)
            .subscribe(suggestions::postValue)
    }

    @SuppressLint("CheckResult")
    fun onAddingConfirmed() {
        entering.value = false
        suggestions.value = emptyList()
        addItem(newItem)
            .andThen(readItems(NonCompleted))
            .map(this::toViewModels)
            .subscribe(items::postValue)
        _newItem = null
    }

    private fun toViewModels(domainModels: List<Item>) = domainModels.map(this::toViewModel)

    private fun toViewModel(domainModel: Item) = ItemViewModel(domainModel.id, domainModel.name)
}
