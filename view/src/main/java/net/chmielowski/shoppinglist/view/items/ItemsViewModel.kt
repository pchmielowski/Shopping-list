package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import net.chmielowski.shoppinglist.*

@SuppressLint("CheckResult")
class ItemsViewModel(
    private val addItem: ActionWithResult<String, Item>,
    private val readItems: ActionWithResult<ReadItemsParams, List<Item>>,
    private val markCompleted: CompletableAction<MarkCompletedParams>
) : ViewModel() {

    val entering = NonNullMutableLiveData<Boolean>(false)
    val items = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())
    val suggestions = NonNullMutableLiveData<List<ItemViewModel>>(emptyList())

    private var _newItem: String? = null
    private val newItem: String
        get() = _newItem ?: throw IllegalStateException("User has not entered a new item name.")

    init {
        readItems(NonCompleted)
            .map(this::toViewModels)
            .subscribe(items::postValue)
    }

    fun onAddNew() {
        entering.value = true
    }

    fun onTextChange(name: String) {
        _newItem = name
        readItems(Completed)
            .map(this::toViewModels)
            .subscribe(suggestions::postValue)
    }

    fun onAddingConfirmed() {
        entering.value = false
        suggestions.value = emptyList()
        addItem(newItem)
            .map { newItem -> items.value + toViewModel(newItem) }
            .subscribe(items::postValue)
        _newItem = null
    }

    fun onToggled(id: Id) {
        val snapshot = items.value
        val updatedItem = snapshot.single { it.id == id }
        val completed = !updatedItem.completed
        markCompleted(MarkCompletedParams(id, completed))
            .subscribe { items.postValue(snapshot.update(updatedItem, completed)) }
    }

    private fun Iterable<ItemViewModel>.update(updatedItem: ItemViewModel, completed: Boolean) =
        map {
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
