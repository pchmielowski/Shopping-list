package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Lazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import net.chmielowski.shoppinglist.AddItemType
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.item.AddItemParams
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.HasDispatcher
import net.chmielowski.shoppinglist.view.helpers.Event
import javax.inject.Inject

class AddItemViewModel(
    private val addItem: AddItemType,
    override val dispatcher: CoroutineDispatcher = IO
) : ViewModel(), HasDispatcher {

    private var shopId: Id? = null

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
            addItem(AddItemParams(newItemName, quantity, shopId ?: error("Shop id not set.")))
            addingCompleted.postValue(Event(Unit))
        }
        newItemName = ""
        quantity = ""
    }
}
