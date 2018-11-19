package net.chmielowski.shoppinglist.view.addshop

import net.chmielowski.shoppinglist.Id

interface IconViewModelMapper {
    fun mapToViewModel(id: Id, selectedId: Id): IconViewModel
}
