package net.chmielowski.shoppinglist.view.addshop

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.Lazy
import net.chmielowski.shoppinglist.ActionWithResult
import net.chmielowski.shoppinglist.view.helpers.Event
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.shop.AddShopParams
import net.chmielowski.shoppinglist.shop.AddShopResult
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import javax.inject.Inject
import kotlin.random.Random

@SuppressLint("CheckResult")
class AddShopViewModel(val addShop: ActionWithResult<AddShopParams, AddShopResult>) : ViewModel() {

    class Factory @Inject constructor(addShop: Lazy<ActionWithResult<AddShopParams, AddShopResult>>) :
        BaseViewModelFactory<AddShopViewModel>({ AddShopViewModel(addShop.get()) })

    val icons = NonNullMutableLiveData<List<IconViewModel>>(createIcons())
    val color = NonNullMutableLiveData<Float>(Random.nextFloat())
    val nameError = MutableLiveData<Event<Unit>>()
    val addingSuccess = MutableLiveData<Event<Id>>()

    private fun createIcons() = LongRange(0, 7).map { IconViewModel.fromId(it) }

    private var enteredName: String? = null

    fun onNameEntered(name: String) {
        enteredName = name
    }

    private var selectedIcon: Id? = null

    fun onIconClicked(icon: Id) {
        fun IconViewModel.shouldBeSelected(clicked: Id) = id == clicked && !isSelected
        icons.value = icons.value.map { it.copy(isSelected = it.shouldBeSelected(icon)) }
        selectedIcon = icon
    }

    fun onColorSelected(hue: Float) {
        assert(0.0f < hue && hue <= 1.0f) { "Color value out of range: $hue" }
        color.value = hue
    }

    fun onAddingConfirmed() {
        if (enteredName.isNullOrEmpty()) {
            nameError.value = Event(Unit)
        } else {
            addShop(AddShopParams(enteredName!!, color.value, selectedIcon))
                .subscribe(this::showResult)
        }
    }

    private fun showResult(result: AddShopResult) = when (result) {
        is AddShopResult.ShopAlreadyPresent -> {
            // TODO("Handle error!")
        }
        is AddShopResult.Success -> {
            addingSuccess.value = Event(result.id)
        }
    }
}
