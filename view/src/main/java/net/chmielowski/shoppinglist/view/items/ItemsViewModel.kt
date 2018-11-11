package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import net.chmielowski.shoppinglist.*
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData

@SuppressLint("CheckResult")
class ItemsViewModel(
    private val addItem: ActionWithResult<String, Item>,
    private val readItems: ActionWithResult<ReadItemsParams, List<Item>>,
    private val markCompleted: CompletableAction<MarkCompletedParams>
) : ViewModel() {

    val isEnteringNew = NonNullMutableLiveData<Boolean>(false)
    val suggestions = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())
    val items = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())

    private var _newItem: String? = null
    private val newItem: String
        get() = _newItem ?: throw IllegalStateException("User has not entered a new item name.")

    init {
        readItems(NonCompleted)
            .map(this::toViewModels)
            .subscribe(items::postValue)
    }

    fun onAddNew() {
        isEnteringNew.value = true
    }

    fun onTextChange(name: String) {
        _newItem = name
        readItems(Completed)
            .map(this::toViewModels)
            .subscribe(suggestions::postValue)
    }

    fun onAddingConfirmed() {
        isEnteringNew.value = false
        suggestions.value = emptyList()
        addItem(newItem)
            .map { newItem -> items.value + toViewModel(newItem) }
            .subscribe(items::postValue)
        _newItem = null
    }

    fun onSuggestionChosen(item: Id) {
        isEnteringNew.value = false
        _newItem = null
        items.value = items.value + suggestions.findWithId(item)
            .copy(completed = false)
        suggestions.value = emptyList()

        markCompleted(MarkCompletedParams(item, false))
            .subscribe()
    }

    private fun <T : HasId> NonNullMutableLiveData<out Iterable<T>>.findWithId(id: Id) =
        value.single { it.id == id }

    fun onToggled(id: Id) {
        val updatedItem = items.findWithId(id)
        val completed = !updatedItem.completed
        items.value = items.update(updatedItem, completed)
        markCompleted(MarkCompletedParams(id, completed))
            .subscribe()
    }

    private fun NonNullMutableLiveData<out Iterable<ItemViewModel>>.update(
        updatedItem: ItemViewModel,
        completed: Boolean
    ) =
        value.map {
            when (it) {
                updatedItem -> updatedItem.copy(completed = completed)
                else -> it
            }
        }

    private fun toViewModels(domainModels: Iterable<Item>) = domainModels.map(this::toViewModel)

    private fun toViewModel(domainModel: Item) = ItemViewModel(
        domainModel.id,
        domainModel.name,
        domainModel.completed
    )
}
