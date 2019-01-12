package net.chmielowski.shoppinglist.view.addshop

import net.chmielowski.shoppinglist.IconId

interface IconViewModelMapper {

    fun mapToViewModel(id: IconId, selectedId: IconId): IconViewModel
}
