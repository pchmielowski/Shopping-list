package net.chmielowski.shoppinglist.view.shops

import androidx.lifecycle.MutableLiveData
import net.chmielowski.shoppinglist.Event
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData
import kotlin.random.Random

class AddShopViewModel {
    val icons = NonNullMutableLiveData<List<IconViewModel>>(createIcons())
    val color = NonNullMutableLiveData<Float>(Random.nextFloat())
    val nameError = MutableLiveData<Event<Unit>>()
    val addingSuccess = MutableLiveData<Event<Unit>>()

    private fun createIcons() = LongRange(0, 7).map { IconViewModel.fromId(it) }

    fun onIconClicked(icon: Id) {
        icons.value = icons.value.map { it.copy(isSelected = it.shouldBeSelected(icon)) }
    }

    private fun IconViewModel.shouldBeSelected(clicked: Id) = id == clicked && !isSelected

    fun onColorSelected(hue: Float) {
        assert(0.0f < hue && hue <= 1.0f)
        color.value = hue
    }

    fun onAddingConfirmed() {
        if (name.isNullOrEmpty()) {
            nameError.value = Event(Unit)
        } else {
            // TODO: wait for use case to finish
            addingSuccess.value = Event(Unit)
        }
    }

    private var name: String? = null

    fun onNameEntered(name: String) {
        this.name = name
    }
}
