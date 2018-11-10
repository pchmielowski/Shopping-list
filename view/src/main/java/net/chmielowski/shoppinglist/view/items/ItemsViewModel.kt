package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.chmielowski.shoppinglist.Item
import net.chmielowski.shoppinglist.ReadAction
import net.chmielowski.shoppinglist.ReadItemsParams
import net.chmielowski.shoppinglist.WriteAction

class ItemsViewModel(
    private val add: WriteAction<String>,
    private val read: ReadAction<ReadItemsParams, List<Item>>
) : ViewModel() {

    val entering = MutableLiveData<Boolean>()
    val items = MutableLiveData<List<ItemViewModel>>()

    private var newItem: String? = null

    fun onAddNew() {
        entering.value = true
    }

    fun onTextChange(name: String) {
        newItem = name
    }

    @SuppressLint("CheckResult")
    fun onAddingConfirmed() {
        entering.value = false
        add(newItem!!)
            .andThen(read(ReadItemsParams))
            .map { list -> list.map(this::toViewModel) }
            .subscribe(items::postValue)
        newItem = null
    }

    private fun toViewModel(domainModel: Item) = ItemViewModel(domainModel.id, domainModel.name)
}
