package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.chmielowski.shoppinglist.Item
import net.chmielowski.shoppinglist.ReadAction
import net.chmielowski.shoppinglist.ReadItemsParams
import net.chmielowski.shoppinglist.WriteAction
import java.lang.IllegalStateException

class ItemsViewModel(
    private val addItem: WriteAction<String>,
    private val readItems: ReadAction<ReadItemsParams, List<Item>>
) : ViewModel() {

    val entering = MutableLiveData<Boolean>()
    val items = MutableLiveData<List<ItemViewModel>>()

    private var _newItem: String? = null
    private val newItem: String
        get() = _newItem ?: throw IllegalStateException("User has not entered a new item name.")

    fun onAddNew() {
        entering.value = true
    }

    fun onTextChange(name: String) {
        _newItem = name
    }

    @SuppressLint("CheckResult")
    fun onAddingConfirmed() {
        entering.value = false
        addItem(newItem)
            .andThen(readItems(ReadItemsParams))
            .map { list -> list.map(this::toViewModel) }
            .subscribe(items::postValue)
        _newItem = null
    }

    private fun toViewModel(domainModel: Item) = ItemViewModel(domainModel.id, domainModel.name)
}
