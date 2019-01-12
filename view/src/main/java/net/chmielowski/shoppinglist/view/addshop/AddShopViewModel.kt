package net.chmielowski.shoppinglist.view.addshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import net.chmielowski.shoppinglist.IconId
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.shop.AddShopResult
import net.chmielowski.shoppinglist.shop.ShopColor
import net.chmielowski.shoppinglist.shop.ShopRepository
import net.chmielowski.shoppinglist.view.HasDispatcher
import net.chmielowski.shoppinglist.view.helpers.Event
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData

class AddShopViewModel(
    private val repository: ShopRepository,
    private val iconMapper: IconViewModelMapper,
    override val dispatcher: CoroutineDispatcher
) : ViewModel(), HasDispatcher {

    val icons = NonNullMutableLiveData<List<IconViewModel>>(createIcons())
    val addingResult = MutableLiveData<Event<Result>>()

    sealed class Result {
        object EmptyName : Result()
        object ShopExists : Result()
        data class ShopAdded(val newShopId: ShopId) : Result()
    }

    private fun createIcons() = IntRange(0, 7).map { iconMapper.mapToViewModel(IconId(it), selectedIcon) }

    private var enteredName: String? = null

    fun onNameEntered(name: String) {
        enteredName = name
    }

    private var selectedIcon: IconId = IconId(0)

    fun onIconClicked(icon: IconId) {
        fun IconViewModel.shouldBeSelected(clicked: IconId) = id == clicked
        icons.value = icons.value.map { it.copy(isSelected = it.shouldBeSelected(icon)) }
        selectedIcon = icon
    }

    private var selectedColor: ShopColor? = null

    fun onColorSelected(color: Pair<Int, Int>) {
        selectedColor = color
    }

    fun onAddingConfirmed() {
        if (enteredName.isNullOrEmpty()) {
            addingResult.value = Event(Result.EmptyName)
        } else {
            launch {
                val result = repository.add(enteredName!!, selectedColor, selectedIcon)
                    .toViewModelResult()
                addingResult.postValue(Event(result))
            }
        }
    }

    private fun AddShopResult.toViewModelResult() = when (this) {
        is AddShopResult.ShopAlreadyPresent -> Result.ShopExists
        is AddShopResult.Success -> Result.ShopAdded(id)
    }
}
