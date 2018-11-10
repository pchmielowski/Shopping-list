package net.chmielowski.shoppinglist.view.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.chmielowski.shoppinglist.Item
import net.chmielowski.shoppinglist.ReadAction
import net.chmielowski.shoppinglist.WriteAction

class ItemsViewModel(
    private val add: WriteAction<Item>,
    private val read: ReadAction<out ReadAction.Params, List<Item>>
) : ViewModel() {

    val items = MutableLiveData<List<ItemViewModel>>()

    init {
        items.value = emptyList()
    }

    fun addItem(name: String) {
        items.value = listOf(ItemViewModel(0, name))
    }
}