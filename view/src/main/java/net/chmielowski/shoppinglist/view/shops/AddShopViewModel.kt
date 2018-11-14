package net.chmielowski.shoppinglist.view.shops

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import net.chmielowski.shoppinglist.ActionWithResult
import net.chmielowski.shoppinglist.Event
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.shop.AddShopParams
import net.chmielowski.shoppinglist.shop.AddShopResult
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import kotlin.random.Random


class AddShopViewModel(val addShop: ActionWithResult<AddShopParams, AddShopResult>) {
    val icons = NonNullMutableLiveData<List<IconViewModel>>(createIcons())
    val color = NonNullMutableLiveData<Float>(Random.nextFloat())
    val nameError = MutableLiveData<Event<Unit>>()
    val addingSuccess = MutableLiveData<Event<Id>>()

    private fun createIcons() = LongRange(0, 7).map { IconViewModel.fromId(it) }

    fun onIconClicked(icon: Id) {
        icons.value = icons.value.map { it.copy(isSelected = it.shouldBeSelected(icon)) }
    }

    private fun IconViewModel.shouldBeSelected(clicked: Id) = id == clicked && !isSelected

    fun onColorSelected(hue: Float) {
        assert(0.0f < hue && hue <= 1.0f) { "Color value out of range: $hue" }
        color.value = hue
    }

    @SuppressLint("CheckResult")
    fun onAddingConfirmed() {
        if (name.isNullOrEmpty()) {
            nameError.value = Event(Unit)
        } else {
            addShop(AddShopParams(name!!, .4f, 23))
                .subscribe { result ->
                    when (result) {
                        is AddShopResult.ShopAlreadyPresent -> {
                            // TODO: show error
                        }
                        is AddShopResult.Success -> {
                            addingSuccess.value = Event(result.id)
                        }
                    }
                }
        }
    }

    private var name: String? = null

    fun onNameEntered(name: String) {
        this.name = name
    }
}
