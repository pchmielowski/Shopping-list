package net.chmielowski.shoppinglist.view.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemsViewModel : ViewModel() {
    val items = MutableLiveData<List<ItemViewModel>>()

    init {
        items.value = emptyList()
    }
}