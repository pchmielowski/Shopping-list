package net.chmielowski.shoppinglist.view.addshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import net.chmielowski.shoppinglist.AddShopType
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.shop.AddShopParams
import net.chmielowski.shoppinglist.shop.AddShopResult
import net.chmielowski.shoppinglist.shop.ShopColor
import net.chmielowski.shoppinglist.view.HasDispatcher
import net.chmielowski.shoppinglist.view.helpers.Event
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData

class AddShopViewModel(
    private val addShop: AddShopType,
    private val iconMapper: IconViewModelMapper,
    override val dispatcher: CoroutineDispatcher = IO
) : ViewModel(), HasDispatcher {

    val icons = NonNullMutableLiveData<List<IconViewModel>>(createIcons())
    val addingResult = MutableLiveData<Event<Result>>()

    sealed class Result {
        object EmptyName : Result()
        object ShopExists : Result()
        data class ShopAdded(val newShopId: Id) : Result()
    }

    private fun createIcons() = LongRange(0, 7).map { iconMapper.mapToViewModel(it, selectedIcon) }

    private var enteredName: String? = null

    fun onNameEntered(name: String) {
        enteredName = name
    }

    private var selectedIcon: Id = 0

    fun onIconClicked(icon: Id) {
        fun IconViewModel.shouldBeSelected(clicked: Id) = id == clicked
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
                val result = addShop(AddShopParams(enteredName!!, selectedColor, selectedIcon))
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
