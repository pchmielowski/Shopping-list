package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Lazy
import net.chmielowski.shoppinglist.AddItemType
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.helpers.Event
import javax.inject.Inject

class AddItemViewModel(private val addItem: AddItemType, private val shopId: Id) : ViewModel() {

    class Factory(addItem: Lazy<AddItemType>, shopId: Id) :
        BaseViewModelFactory<AddItemViewModel>({ AddItemViewModel(addItem.get(), shopId) }) {
        class Builder @Inject constructor(private val addItem: Lazy<AddItemType>) {
            fun build(shopId: Id) = Factory(addItem, shopId)
        }
    }

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
        addItem(AddItemParams(newItemName, quantity, shopId))
            .subscribe {
                addingCompleted.postValue(Event(Unit))
            }
        newItemName = ""
        quantity = ""
    }
}
