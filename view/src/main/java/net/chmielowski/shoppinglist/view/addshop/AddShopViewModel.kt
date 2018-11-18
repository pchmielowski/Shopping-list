package net.chmielowski.shoppinglist.view.addshop

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Lazy
import net.chmielowski.shoppinglist.AddShopType
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ShopColor
import net.chmielowski.shoppinglist.shop.AddShopParams
import net.chmielowski.shoppinglist.shop.AddShopResult
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.helpers.Event
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import javax.inject.Inject

@SuppressLint("CheckResult")
class AddShopViewModel(val addShop: AddShopType) : ViewModel() {

    class Factory @Inject constructor(addShop: Lazy<AddShopType>) :
        BaseViewModelFactory<AddShopViewModel>({ AddShopViewModel(addShop.get()) })

    val icons = NonNullMutableLiveData<List<IconViewModel>>(createIcons())
    val addingResult = MutableLiveData<Event<Result>>()

    sealed class Result {
        object EmptyName : Result()
        object ShopExists : Result()
        data class ShopAdded(val newShopId: Id) : Result()
    }

    private fun createIcons() = LongRange(0, 7).map { IconViewModel.fromId(it, selectedIcon) }

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
            addShop(AddShopParams(enteredName!!, selectedColor, selectedIcon))
                .map(this::toViewModelResult)
                .map { Event(it) }
                .subscribe(addingResult::postValue)
        }
    }

    private fun toViewModelResult(result: AddShopResult) = when (result) {
        is AddShopResult.ShopAlreadyPresent -> Result.ShopExists
        is AddShopResult.Success -> Result.ShopAdded(result.id)
    }
}
