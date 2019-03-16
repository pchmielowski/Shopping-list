package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.item.ItemRepository
import net.chmielowski.utils.event.Event

class AddItemViewModel(private val repository: ItemRepository, private val shop: ShopId) :
    ViewModel() {

    val addingCompleted = MutableLiveData<Event<Unit>>()
    val newItemNameError = MutableLiveData<Event<Unit>>()

    private var newItemName: String = ""
    private var quantity: String = ""

    fun onNewItemNameChange(name: String) {
        newItemName = name
    }

    fun onQuantityChange(qntty: String) {
        quantity = qntty
    }

    @SuppressLint("CheckResult")
    fun onAddingConfirmed() {
        if (newItemName.isBlank()) {
            newItemNameError.postValue(Event(Unit))
            return
        }
        viewModelScope.launch {
            repository.add(newItemName, quantity, shop)
            addingCompleted.postValue(Event(Unit))
        }
        newItemName = ""
        quantity = ""
    }
}
