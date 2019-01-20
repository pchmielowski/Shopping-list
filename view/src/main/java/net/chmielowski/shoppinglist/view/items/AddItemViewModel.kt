package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.item.ItemRepository
import net.chmielowski.shoppinglist.view.HasDispatcher
import net.chmielowski.shoppinglist.view.helpers.Event


class AddItemViewModel(
    private val repository: ItemRepository,
    private val shop: ShopId,
    override val dispatcher: CoroutineDispatcher
) : ViewModel(), HasDispatcher {

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
        launch {
            repository.add(newItemName, quantity, shop)
            addingCompleted.postValue(Event(Unit))
        }
        newItemName = ""
        quantity = ""
    }
}
