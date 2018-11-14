package net.chmielowski.shoppinglist.view.shops

import net.chmielowski.shoppinglist.view.helpers.NonNullMutableLiveData

class AddShopViewModel {
    val icons = NonNullMutableLiveData<List<IconViewModel>>(createIcons())

    private fun createIcons() = LongRange(0, 7).map { IconViewModel.fromId(it) }
}
