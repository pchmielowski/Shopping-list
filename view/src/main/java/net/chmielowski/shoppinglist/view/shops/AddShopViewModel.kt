package net.chmielowski.shoppinglist.view.shops

import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData

class AddShopViewModel {
    val icons = NonNullMutableLiveData<List<IconViewModel>>(createIcons())
    val color = NonNullMutableLiveData<Float>(0.0f) // TODO: Random

    private fun createIcons() = LongRange(0, 7).map { IconViewModel.fromId(it) }

    fun onIconClicked(icon: Id) {
        icons.value = icons.value.map { it.copy(isSelected = it.id == icon) }
    }

    fun colorSelected(hue: Float) {
        color.value = hue
    }
}
